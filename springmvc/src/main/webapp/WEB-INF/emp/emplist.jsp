<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xyf
  Date: 2017/12/3
  Time: 0:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<script type="text/javascript" src="../scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".delete").click(function () {
            var href = $(this).attr("href");
            $(".delForm").attr("action",href).submit();
            return false;
        });
    })
</script>
<html>
<head>
    <title>雇员列表页面</title>
</head>
<body>

<form class="delForm" action="" method="post">
    <input type="hidden" name="_method" value="delete"/>
</form>
<c:if test="${empty requestScope.emps}">
    <p>目前没有雇员信息</p>
</c:if>
<c:if test="${not empty requestScope.emps}">
    <table border="1" cellspacing="0" cellpadding="10">
        <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>邮箱</th>
            <th>性别</th>
            <th>部门</th>
            <th>修改</th>
            <th>删除</th>
        </tr>

        <c:forEach items="${requestScope.emps}" var="emp" >
            <tr>
                <td>${emp.id}</td>
                <td>${emp.name}</td>
                <td>${emp.email}</td>
                <td>${emp.gender==1?"男":"女"}</td>
                <td>${emp.dept.deptName}</td>
                <td width="50"><a href="emp/${emp.id}">修改</a></td>
                <td width="50"><a class="delete" href="emp/${emp.id}">删除</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<hr style="width: auto"/>
<a href="emp">新增</a>
</body>
</html>
