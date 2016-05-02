<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.PrintWriter" %>
<html>
<head>
    <title>Exception</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<div class="container">
    <%@ include file="navbar.jsp" %>

    Sorry, Exception was threw in the server.
    <br>
<pre>
    <c:out value="${pageContext.exception.message}"/>
<%exception.printStackTrace(new PrintWriter(out));%>
    <%@ include file="footer.jsp" %>
</pre>
</div>

</body>
</html>
