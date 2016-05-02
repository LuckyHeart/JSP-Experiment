<%--
  Created by IntelliJ IDEA.
  User: Nigel
  Date: 2016/4/22
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>${requestScope.title}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="refresh" content="3; url=/login">
</head>

<body>
<div class="container">
    <%@ include file="navbar.jsp" %>
    <div class="text-center">
        <h1>${requestScope.title}</h1>
        <h2>${requestScope.msg}</h2>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>