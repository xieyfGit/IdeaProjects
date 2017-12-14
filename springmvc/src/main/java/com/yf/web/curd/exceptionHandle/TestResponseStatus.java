package com.yf.web.curd.exceptionHandle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 使用Jetty时，@ResponseStatus返回的reason中文乱码原因：
 * 跟踪源码至方法知：org.eclipse.jetty.server.handler.ErrorHandler.getAcceptableWriter会在请求头中查找
 * Accept-Charset，若为空则response.setCharacterEncoding(StandardCharsets.ISO_8859_1.name());
 * 导致中文乱码，而Request暴露出来的接口根本不允许进行Header属性的更改，所以我认为jetty这是个SB操作。
 *
 *   解决办法：前端发送请求时，设置Accept-Charset为UTF-8。
 */

@ResponseStatus(reason = "除数为1不被允许!", code = HttpStatus.FORBIDDEN)
public class TestResponseStatus extends Exception {
    private static final long serialVersionUID = 1012523363063630295L;

}