<%--
  Created by IntelliJ IDEA.
  User: xyf
  Date: 2017/11/30
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true"  %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Success Page</title>
</head>
<body>
<p>Success Page</p>
<p>requestScope-user:${requestScope.user}</p>
<p>requestScope-user1:${requestScope.user1}</p>
<p>requestScope-user0:${requestScope.user0}</p>
<p>requestScope.name:${requestScope.name}</p>
<p>sessionScope.name:${sessionScope.name}</p>
<p>requestScope.age${requestScope.age}</p>
<p>requestScope.date${requestScope.date}</p>
<p>sessionScope.user:${sessionScope.user}</p>
<p>国际化资源文件测试</p>
<table border="1" style="width: auto">
    <tr ><td width="100"><fmt:message key="i18n.username"/></td></tr>
    <tr ><td width="100"><fmt:message key="i18n.password"/></td>></tr>
</table>

</body>
</html>
