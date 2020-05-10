<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>简历列表</title>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
<table border="1">
    <tr>
        <td>id</td>
        <td>用户名</td>
        <td>地址</td>
        <td>手机号</td>
        <td></td>
        <td><a href="${pageContext.request.contextPath}/toAdd">添加</a></td>
    </tr>
    <c:forEach items="${list}" var="obj">
        <tr>
            <td>${obj.id}</td>
            <td>${obj.name}</td>
            <td>${obj.address}</td>
            <td>${obj.phone}</td>
            <td><a href="${pageContext.request.contextPath}/edit?id=${obj.id}">修改</a></td>
            <td><a href="${pageContext.request.contextPath}/delete?id=${obj.id}">删除</a></td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
