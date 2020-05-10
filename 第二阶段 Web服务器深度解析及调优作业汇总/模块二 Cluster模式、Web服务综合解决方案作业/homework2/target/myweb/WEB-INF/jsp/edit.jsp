<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<html>
<head>
    <title>简历列表</title>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    <script type="text/javascript">

    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/update" method="post">
    <input type="hidden" name="id" value="${resume.id}"/>
    <p>
        用户名：<input name="name" type="text" value="${resume.name}">
    </p>
    <p>
        地址：<input name="address" type="address" value="${resume.address}">
    </p>
    <p>
        手机号：<input name="phone" type="text" value="${resume.phone}">
    </p>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
