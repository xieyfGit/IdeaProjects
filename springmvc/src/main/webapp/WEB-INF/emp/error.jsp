<%--
  Created by IntelliJ IDEA.
  User: xyf
  Date: 2017/12/4
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p4>Error Page</p4>
<p>错误信息:</p>
<p>@ExceptionHandler处理结果</p>
<p>${requestScope.exception}</p>
<p>SimpleMappingExceptionResolver处理结果</p>
<p>${requestScope.ex}</p>
<%
String var = (String) request.getAttribute("javax.servlet.error.request_uri");
//参考DispatcherServlet.WebUtils.exposeErrorRequestAttributes中设置的错误信息
/*    public static final String ERROR_STATUS_CODE_ATTRIBUTE = "javax.servlet.error.status_code";
    public static final String ERROR_EXCEPTION_TYPE_ATTRIBUTE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE_ATTRIBUTE = "javax.servlet.error.message";
    public static final String ERROR_EXCEPTION_ATTRIBUTE = "javax.servlet.error.exception";
    public static final String ERROR_REQUEST_URI_ATTRIBUTE = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME_ATTRIBUTE = "javax.servlet.error.servlet_name";*/
request.setAttribute("var",var);
%>
<p>${var}</p>
</body>
</html>
