<%@ page pageEncoding="utf-8" isELIgnored="false" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>登录</h2>
${message}
<form action="${pageContext.request.contextPath}/doLogin" method="post">
    用户名：<input name="username" type="text">
    密码：<input name="password" type="password">
    <input type="submit" value="登录"/>
</form>
</body>
</html>
