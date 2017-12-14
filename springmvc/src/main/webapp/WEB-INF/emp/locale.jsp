<%--
  Created by IntelliJ IDEA.
  User: xyf
  Date: 2017/12/4
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!--
    1.在页面上能根据浏览器语言设置的情况对文本（指绑定的属性）、时间、类型进行本地化处理
    2.可以在Bean中获取国际化资源文件Locale对应消息
    3.可以通过超链接切换Locale，而不再依赖浏览器设置

    解决方式：
    1.使用JSTL fmt标签
    2.在ioc容器中注入ResourceBundleMessageSource实例，使用其bean实例的getMessage方法即可
    3.配置LocaleResolver和LocaleChangeInterceptor
 -->
<fmt:message key="i18n.username"></fmt:message>


<a href="locale?locale=zh_CN">locale?locale=zh_CN</a>
<a href="locale?locale=en_US">locale?locale=en_US</a>
<br/>
</body>
</html>
