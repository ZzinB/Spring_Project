<%--
  Created by IntelliJ IDEA.
  User: shinbi
  Date: 2024/03/07
  Time: 1:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/todo/register" method="post">
  <div>
    <input type="text" name="title" placeholder="INSERT TITLE">
  </div>
  <div>
    <input type="date" name="dueDate">
  </div>
  <div>
    <button type="reset">RESET</button>
    <button type="submit">REGISTER</button>
  </div>
</form>
</body>
</html>
