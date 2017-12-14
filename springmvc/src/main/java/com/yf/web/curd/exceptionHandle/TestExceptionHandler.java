package com.yf.web.curd.exceptionHandle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 1.@ExceptionHandler目标方法可以添加Exception类型入参以获取错误对象
 * 2.@ExceptionHandler目标方法不能在入参中传入Map设置请求域错误信息，需要返回ModelAndView方式
 * 3.@ExceptionHandler标记的异常优先级与目标异常的相似度来匹配，相似度最高者匹配。
 * 4.@ExceptionHandle可以直接注解在@Controller注解类的方法上，效果相同
 */
@ControllerAdvice
public class TestExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeExHandle(Exception ex) {
        System.out.println("handle a exception typed :" + RuntimeException.class.getSimpleName());
        return initMv(ex);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView mathExHandle(Exception ex) {
        System.out.println("handle a exception typed :" + ArithmeticException.class.getSimpleName());
        return initMv(ex);
    }

    private ModelAndView initMv(Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("exception", ex);
        return mv;
    }
}
