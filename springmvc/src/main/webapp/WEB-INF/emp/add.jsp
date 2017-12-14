<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: xyf
  Date: 2017/12/3
  Time: 0:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="../scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#testJson").click(function () {
            var url = this.href;
            var args = {};
            $.post(url, args, function (data) {
                for (var i = 0; i < data.length; i++) {
                    alert(data[i].deptNo + ":" + data[i].deptName);
                }
                return false;
            });
        });
    })

</script>
<html>
<head>
    <title>新增雇员页面</title>
</head>
<body>

<%--发送ajax，获取json数据--%>
<p>发送ajax，获取json数据</p>
<p><a href=depts" id="testJson"> 获取所有部门信息的JsoN数据</a></p>

<p>@RequestBody、@ResponseBody</p>
<p>下载</p>
<a href="download">下载a.txt</a>
<p>读取上传文件内容</p>
<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
    <%--不适用name参数则读取不到file内容--%>
    <input type="file" name="file" value="请选择路径"/>
    <label><input type="text" name="desc" value="请输入文件描述"></label>
    <input type="submit" value="上传" >
</form>
<p>上传文件</p>
<form action="${pageContext.request.contextPath}/uploadFile" method="post" enctype="multipart/form-data">
    <%--不适用name参数则读取不到file内容--%>
    <input type="file" name="file" value="请选择路径"/>
    <label><input type="text" name="desc" value="请输入文件描述"></label>
    <input type="submit" value="上传" >
</form>
<%--使用自定义属性绑定器添加雇员--%>
<p>使用自定义属性绑定器添加雇员，添加格式：name-email-gender-deptNo如：tom-tom@163.com-1-1001</p>
<form action="${pageContext.request.contextPath }/empAddViaConverter" method="post">
    <label><input type="text" name="emp"/></label>
    <input type="submit" value="新增用户"/>
</form>

<%--action在跳转时会默认将当前相对于WEB-INF目录的路径加入到url中，若jsp不在WEB-INF根目录下，则会导致url错误--%>
<p>使用框架默认属性绑定器添加雇员</p>
<form:form action="${pageContext.request.contextPath }/emp" method="post" modelAttribute="emp">
    <c:if test="${requestScope.emp.id == null}">
        姓名： <form:input path="name"/> <form:errors path="name" cssStyle="color: red"/><br/>
    </c:if>
    <c:if test="${requestScope.emp.id != null}">
        <form:hidden path="id"/>
        <input type="hidden" name="_method" value="put"/>
    </c:if>
    邮箱：<form:input path="email"/><form:errors path="email" cssStyle="color: red"/><br/>
    <%
        Map<Integer, String> genders = new HashMap<>();
        genders.put(1, "男");
        genders.put(0, "女");

        request.setAttribute("genders", genders);
    %>
    性别：<form:radiobuttons path="gender" items="${genders}" cssStyle="color: red"/><br/>
    <%--
         1.数据类型转换
         2.数据类型格式化
         3.数据校验
         1).如何校验？注解：JSR303
         2).校验出错转向哪一个页面？
         3).错误消息？如何显示，如何将错误消息国际化
    --%>
    生日：<form:input path="birth"/><form:errors path="birth" cssStyle="color: red"/><br/>
    薪资：<form:input path="salary"/><br/>
    部门：<form:select path="dept.deptNo" items="${requestScope.depts}" itemLabel="deptName" itemValue="deptNo"/><br/>
    <input type="submit" value="提交"/>
</form:form>
</body>
</html>
