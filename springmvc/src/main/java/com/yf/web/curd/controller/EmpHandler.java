package com.yf.web.curd.controller;

import com.yf.web.curd.entity.Dept;
import com.yf.web.curd.entity.Emp;
import com.yf.web.curd.exceptionHandle.TestResponseStatus;
import com.yf.web.curd.service.DeptService;
import com.yf.web.curd.service.EmpService;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Controller
public class EmpHandler {

    @Resource
    private EmpService empService;

    @Resource
    private DeptService deptService;

    @Resource
    private HttpSession session;

    @Resource
    private ResourceBundleMessageSource messageSource;

    //SimpleMappingExceptionResolver异常处理测试，参考SimpleMappingExceptionResolver.doResolveException方法
    //1.可以定制错误信息返回页面，通过bean配置实现
    //2.自动返回一个ModelAndView，model中错误对象的键名默认为exception，可以通过bean配置设置，默认未开启，需要手动注入到IOC容器
    //3.和@ExceptionHandler同时存在时，@ExceptionHandler优先处理
    @RequestMapping("/simExHandler")
    public void simExHandler() throws Exception {
        System.out.println("SimpleMappingExceptionResolver异常处理测试...");
        int[] ints = new int[2];
        System.out.println(ints[10]);
    }

    //DefaultExceptionHandler测试；参考DefaultHandlerExceptionResolver.doResolveException,可以处理的异常类型
    //异常类型：SpringMVC自定义的类型映射相关异常、以及ServletException子类异常
    @RequestMapping(value = "/defExHandler", method = RequestMethod.POST)
    public String defExHandler() {
        System.out.println("DefaultHandlerExceptionResolver异常处理测试...");
        return "success";
    }


    //区域语言测试、@ExceptionHandler测试、@ResponseStatus测试
    @RequestMapping(value = "/locale")
    public String localeTest(Locale locale, @RequestParam(required = false, defaultValue = "2") int num) throws TestResponseStatus {
        System.out.println(messageSource.getMessage("i18n.username", null, locale));
        if (num == 1) {
            throw new TestResponseStatus();
        }
        int i = 10 / num;
        return "locale";
    }

    //HttpMessageConverter：自动根据返回类型寻找注册的converter
    //下载功能
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download() throws IOException {
        byte[] data = null;
        ServletContext servletContext = session.getServletContext();
        InputStream in = servletContext.getResourceAsStream("/file/a.txt");

        data = new byte[in.available()];
        in.read(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=a.txt");

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<byte[]>(data, headers, status);
    }

    //上传功能
    //1.配置MultipartResolver的实现接口Bean
    //2.web.xml配置<multipart-config>
    //遇到一个问题：页面中上传时<input/>标签的name属性必须指定，否则读取不到文件内容，原因分析：SpringMVC在获取参数时
    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("desc") String desc) throws IOException {
        System.out.println("desc:" + desc);
        String filePath = session.getServletContext().getRealPath("") + "\\upload\\";
        File save = new File(filePath);
        if (!save.exists()) {
            save.mkdirs();
        }
        File real = new File(filePath, file.getOriginalFilename());
        if (!real.exists()) {
            real.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(real);
        byte[] data = new byte[file.getInputStream().available()];
        file.getInputStream().read(data);
        fos.write(data);
        return "upload success" + new Date();
    }

    //上传内容读取
    //正常上传功能启用下不可用，需要去掉对应配置
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestBody String up) {
        System.out.println(up);
        return "upload success" + new Date();
    }


    @ResponseBody
    @RequestMapping("/depts")
    public Collection<Dept> depts() {
        return deptService.getAll();
    }

    //使用自定义的类型转换器实现属性绑定，参考ModelAttributeMethodProcessor.java:110
    @RequestMapping(value = "/empAddViaConverter", method = RequestMethod.POST)
    public String empAddViaConverter(@RequestParam @Valid Emp emp) {
        empService.addOne(emp);
        return "redirect:/emps";
    }

    @ModelAttribute
    public void initEmp(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("emp", empService.getOne(id));
        }
    }

    //新增雇员页面跳转
    @RequestMapping(value = "/emp", method = RequestMethod.GET)
    public String toEmpAdd(Model model) {
        model.addAttribute("emp", new Emp());
        model.addAttribute("depts", deptService.getAll());
        return "add";
    }

    //新增雇员
    //测试类型转换错误信息获取,BindingResult作为接收WebDataBinder处理属性转换异常接收对象
    //需要校验的对象和BindingResult之间不能有其它入参，否则ModelAttributeMethodProcessor.resolveArgument会报错错:参考isBindExceptionRequired方法
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    public String empAdd(Map<String, Object> map, @Valid Emp emp, BindingResult result) {
        if (result.getErrorCount() > 0) {
            System.out.println("类型转换出错...");
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ":" + error.getDefaultMessage());
            }
            map.put("emp", emp);
            map.put("depts", deptService.getAll());
            return "add";
        } else {
            empService.addOne(emp);
        }
        return "redirect:/emps";
    }


    //修改雇员页面跳转
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    public String toEmpAdd(@PathVariable Integer id, Model model) {
        model.addAttribute("emp", empService.getOne(id));
        model.addAttribute("depts", deptService.getAll());
        return "add";
    }

    //修改雇员,name不能修改
    @RequestMapping(value = "/emp", method = RequestMethod.PUT)
    public String empEdit(@ModelAttribute("emp") @Valid Emp emp) {
        empService.addOne(emp);
        return "redirect:/emps";
    }


    //雇员列表
    @RequestMapping(value = "/emps", method = {RequestMethod.GET})
    public ModelAndView emps() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("emps", empService.getAll());
        mv.setViewName("emplist");
        return mv;
    }


    //删除雇员
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
    public String empDel(@PathVariable Integer id, HttpServletRequest request) {
        empService.delOne(id);
        //redirect的具体方法有servlet容器实现，若url以/开始，则以web根目录路径拼接url，
        // 不以/开始，则以请求的url最后一个/之前的相拼接，本例则/emp/emps
        //具体实现需要参考servlet容器的HttpServletResponse实现类的sendRedirect方法
        //顺便看了下转发 ，转发的时候会将当前url所有信息一起转发，包括请求方法
        return "redirect:/emps";
    }

    //自定义View视图解析
    @RequestMapping("/helloView")
    public String helloView() {
        System.out.println("hello view");
//        ModelAndView mv = new ModelAndView();
//        View view = new HelloView();
//        mv.setView(view);
//        return mv;
        return "helloView";
    }

    //用于初始化配置WebDataBinder，定制属性绑定格式化
//    @InitBinder
//    public void initBinder(WebDataBinder webDataBinder) {
//        webDataBinder.setDisallowedFields("name");
//    }


    public EmpHandler() {
        System.out.println("com.yf.web.curd.controller.EmpHandler constructor...");
    }
}
