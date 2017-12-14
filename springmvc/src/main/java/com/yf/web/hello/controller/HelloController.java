package com.yf.web.hello.controller;

import com.yf.web.hello.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 1.Ant风格，参考testAnt
 * 2.pathVariable,参考testpathVariable
 * 3.PUT\DETETE请求发送：①web.xml中添加hiddenHttpMethodFilter②发送post请求，并添加隐藏域的_method参数，指明PUT或者DELETE
 * 4.@PathVariable:绑定url路径中的参数
 * 5.@RequestParam:绑定所有的请求参数，注意url路径中的不算参数，属于url本身，如xx/dd/yy 此时dd不能通过这种方式获取
 * 6.@RequestHeader
 * 7.@CookieValue
 * 8.Servlet原生API注入：HttpServletRequest、HttpServletResponse、HttpSession等
 * 具体可以查看：AnnotationMethodHandlerAdapter.resolveStandardArgument
 * <p>
 * 注意：tomcat8不支持PUT、DELETE提交方式，有三种解决方式：
 * 1.使用tomcat7及以下版本
 * 2.请求先转给一个Controller,再返回jsp页面
 * 3.设置目的页面头文件 中的isErrorPage="true"
 * <%@ page language="java" contentType="text/html; charset=UTF-8"
 * pageEncoding="UTF-8" isErrorPage="true"%>
 */
@Controller
//@Scope("request")
//@SessionAttributes(names = {"user",}, types = {User.class,})
public class HelloController {

    private static final String SUCCESS = "success";
    private Logger logger = LoggerFactory.getLogger(HelloController.class);

    //重定向和转发参考UrlBasedViewResolver::createView具体原理
    @RequestMapping("/redirect")
    public String redirect(RedirectAttributes map,Model model) {
        //在跳转后的url中添加参数
        //会替换掉@ModelAttribute中user对象的name属性，且在url中新增参数：http://localhost:8080/testModelAttribute?name=testRedirect1
        map.addAttribute("name", "testRedirect1");
        //可以传参到重定向的页面，但是刷新页面后参数就失效
        map.addFlashAttribute("name", "testRedirect2");
        model.addAttribute("name","testRedirect3");
        System.out.println("redirect...");
        //不能直接访问WEB-INF目录下的文件，只能通过重定向到另外一个请求
//        return "redirect:spring/success.jsp";
        return "redirect:testModelAttribute";
    }

    @RequestMapping("/forward")
    public String forward(Map<String, Object> map,HttpServletRequest request) {
        request.setAttribute("name", "testForward");
        map.put("name", "testForward");
        System.out.println("forward...");
        return "forward:testModelAttribute";
    }


    /**
     * 1. 有 @ModelAttribute 标记的方法, 会在每个目标方法执行之前被 SpringMVC 调用!
     * 2. @ModelAttribute 注解也可以来修饰目标方法 POJO 类型的入参, 其 value 属性值有如下的作用:
     * 1). SpringMVC 会使用 value 属性值在 implicitModel 中查找对应的对象, 若存在则会直接传入到目标方法的入参中.
     * 2). SpringMVC 会以  value 为 key, POJO 类型的对象为 value, 存入到 request 中.
     */
    @ModelAttribute
    public void getUser(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        User user = new User("tom", 22, 1);
        System.out.println("从数据库中查询对应ID的对象" + user);
        map.put("user0", user);
    }

    /**
     * 运行流程:
     * 1. 执行 @ModelAttribute 注解修饰的方法: 从数据库中取出对象, 把对象放入到了 Map 中. 键为: user
     * 2. SpringMVC 从 Map 中取出 User 对象, 并把表单的请求参数赋给该 User 对象的对应属性.
     * 3. SpringMVC 把上述对象传入目标方法的参数.
     * <p>
     * 注意: 在 @ModelAttribute 修饰的方法中, 放入到 Map 时的键需要和目标方法入参类型的第一个字母小写的字符串一致!
     * <p>
     * SpringMVC 确定目标方法 POJO 类型入参的过程
     * 1. 确定一个 key:
     * 1). 若目标方法的 POJO 类型的参数木有使用 @ModelAttribute 作为修饰, 则 key 为 POJO 类名第一个字母的小写
     * 2). 若使用了  @ModelAttribute 来修饰, 则 key 为 @ModelAttribute 注解的 value 属性值.
     * 2. 在 implicitModel 中查找 key 对应的对象, 若存在, 则作为入参传入
     * 1). 若在 @ModelAttribute 标记的方法中在 Map 中保存过, 且 key 和 1 确定的 key 一致, 则会获取到.
     * 3. 若 implicitModel 中不存在 key 对应的对象, 则检查当前的 Handler 是否使用 @SessionAttributes 注解修饰,
     * 若使用了该注解, 且 @SessionAttributes 注解的 value 属性值中包含了 key, 则会从 HttpSession 中来获取 key 所
     * 对应的 value 值, 若存在则直接传入到目标方法的入参中. 若不存在则将抛出异常.
     * 4. 若 Handler 没有标识 @SessionAttributes 注解或 @SessionAttributes 注解的 value 值中不包含 key, 则
     * 会通过反射来创建 POJO 类型的参数, 传入为目标方法的参数
     * 5. SpringMVC 会把 key 和 POJO 类型的对象保存到 implicitModel 中, 进而会保存到 request 中.
     * <p>
     * 源代码分析的流程
     * 1. 调用 @ModelAttribute 注解修饰的方法. 实际上把 @ModelAttribute 方法中 Map 中的数据放在了 implicitModel 中.
     * 2. 解析请求处理器的目标参数, 实际上该目标参数来自于 WebDataBinder 对象的 target 属性
     * 1). 创建 WebDataBinder 对象:
     * ①. 确定 objectName 属性: 若传入的 attrName 属性值为 "", 则 objectName 为类名第一个字母小写.
     * *注意: attrName. 若目标方法的 POJO 属性使用了 @ModelAttribute 来修饰, 则 attrName 值即为 @ModelAttribute
     * 的 value 属性值
     * <p>
     * ②. 确定 target 属性:
     * > 在 implicitModel 中查找 attrName 对应的属性值. 若存在, ok
     * > *若不存在: 则验证当前 Handler 是否使用了 @SessionAttributes 进行修饰, 若使用了, 则尝试从 Session 中
     * 获取 attrName 所对应的属性值. 若 session 中没有对应的属性值, 则抛出了异常.
     * > 若 Handler 没有使用 @SessionAttributes 进行修饰, 或 @SessionAttributes 中没有使用 value 值指定的 key
     * 和 attrName 相匹配, 则通过反射创建了 POJO 对象
     * <p>
     * 2). SpringMVC 把表单的请求参数赋给了 WebDataBinder 的 target 对应的属性.
     * 3). *SpringMVC 会把 WebDataBinder 的 attrName 和 target 给到 implicitModel.
     * 近而传到 request 域对象中.
     * 4). 把 WebDataBinder 的 target 作为参数传递给目标方法的入参.
     *
     * @param user
     * @return
     */
    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(@ModelAttribute("user0") User user, HttpServletRequest request) {
        System.out.println("ContextPath---> " + request.getContextPath());
        System.out.println("RequestURI---> " + request.getRequestURI());
        UrlPathHelper helper = new UrlPathHelper();
        System.out.println("getContextPath---> " + helper.getContextPath(request));
        System.out.println("getLookupPathForRequest---> " + helper.getLookupPathForRequest(request));
        System.out.println("getOriginatingContextPath---> " + helper.getOriginatingContextPath(request));
        System.out.println("getOriginatingRequestUri---> " + helper.getOriginatingRequestUri(request));
        System.out.println("getOriginatingServletPath---> " + helper.getOriginatingServletPath(request));
        System.out.println("getPathWithinApplication---> " + helper.getPathWithinApplication(request));
        System.out.println("getRequestUri---> " + helper.getRequestUri(request));
        System.out.println("getOriginatingQueryString---> " + helper.getOriginatingQueryString(request));
        System.out.println("getPathWithinServletMapping---> " + helper.getPathWithinServletMapping(request));
        System.out.println("getServletPath---> " + helper.getServletPath(request));
        System.out.println("利用@ModelAttribute中得到的对象，填充user空的属性值");
        System.out.println("修改：" + user);
        return SUCCESS;
    }

    @RequestMapping("/testSessionAttribute")
    public String testSessionAttribute(Map<String, Object> map, HttpSession httpSession) {
        httpSession.setMaxInactiveInterval(10);//设置session超时时间，便于测试session域数据
        User user = new User("tom", 12);
        map.put("user", user);
        return SUCCESS;
    }

    @RequestMapping(value = "/testMap", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public String testMap(Map<String, Object> map, ModelMap modelmap, Model model) throws IOException {
        map.put("name", "mrie");
        modelmap.put("date", new Date());
        model.addAttribute("age", 22);
        return SUCCESS;
    }


    //PUT请求本意就是新增，并不会返回对象，故返回mv会报错，此时应该使用GET
    //JSP页面头添加 isErrorPage="true" ，将目的页面作为错误页面，从而忽略tomcat8不支持PUT\DELETE方法，
    @RequestMapping(value = "/putUser", method = RequestMethod.PUT)
    public String testPut(User user, Map<String, Object> map, ModelMap modelmap, Model model) throws IOException {
        System.out.println(user);
        return SUCCESS;//此处并没有到success.jsp，而是直接将字符串返回到页面
    }


    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public ModelAndView testGet(ModelAndView mv) {
        User user = new User("tom", 22, 1);
        System.out.println(user);
        mv.setViewName(SUCCESS);
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping("/testCookie")
    public String testCookie(@CookieValue("JSESSIONID") String session) {
        System.out.println("CookieValue JSESSIONID :" + session);
        return SUCCESS;
    }

    @RequestMapping("/hello")
    public String testRequestHeader(@RequestHeader("host") String header) {
        logger.debug("HelloController->toIndexPage...");
        System.out.println("request host is : " + header);
        return "hello world!";
    }

    @RequestMapping("/success")
    public String success() {
        logger.debug("HelloController->success");
        return SUCCESS;
    }

    //此处的params仅针对url中的参数，对表单不起作用
    @RequestMapping(value = "/testMethod", method = {RequestMethod.PUT, RequestMethod.DELETE},
            headers = {"accept-language=zh-CN,zh;q=0.9,en;q=0.8"})
    public String testPutAndDelete(@RequestParam(name = "age1", required = false, defaultValue = "0") int id,
                                   @RequestParam(name = "age2", required = false, defaultValue = "0") int id2) {
        logger.debug("HelloController->testPutAndDelete! parameter age1= " + id + " age2 " + id2);
        return SUCCESS;
    }

    //此处的params仅针对url中的参数，对表单不起作用
    @RequestMapping(value = "/testMethod", method = {RequestMethod.POST},
            headers = {"accept-language=zh-CN,zh;q=0.9,en;q=0.8"})
    public String testPost(@RequestParam("age") String id) {
        logger.debug("HelloController->testPost! parameter age= " + id);
        return SUCCESS;
    }

    //此处的params仅针对url中的参数，对表单不起作用
    @RequestMapping(value = "/testMethod", method = {RequestMethod.GET}, params = {"name", "age!=20"},
            headers = {"accept-language=zh-CN,zh;q=0.9,en;q=0.8"})
    public String testMethod(@RequestParam String name, @RequestParam int age) {
        System.out.println(name + age);
        logger.debug("HelloController->testMethod!");
        return SUCCESS;
    }

    //Ant风格映射
    @RequestMapping("/testAnt/*/testAnt")
    public String testAnt() {
        System.out.println("testAnt...");
        return SUCCESS;
    }

    @RequestMapping("/testPathVariable/{id}")
    public String testPathVariable(@PathVariable String id) {
        System.out.println(id);
        return SUCCESS;
    }
}
