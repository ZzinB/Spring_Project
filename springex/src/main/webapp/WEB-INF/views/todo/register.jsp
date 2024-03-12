<%--
  Created by IntelliJ IDEA.
  User: shinbi
  Date: 2024/03/12
  Time: 11:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/todo/register" method="post">
  <div>
    Title: <input type="text" name="title">
  </div>
  <div>
    DueDate: <input type="date" name="dueDate" value="2024-03-12">
  </div>
  <div>
    Writer: <input type="text" name="writer">
  </div>
  <div>
    finished: <input type="checkbox" name="finished">
  </div>
  <div>
    <button type="submit">Register</button>
  </div>
</form>
</body>
</html>
