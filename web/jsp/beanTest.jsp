<%--
  Created by IntelliJ IDEA.
  User: Nigel
  Date: 2016/4/13
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Fantastic Show</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <link rel="stylesheet" href="/lib/css/bootstrap.min.css">
    <link rel="stylesheet" href="/lib/bootstrap-fileinput/css/fileinput.min.css">
    <style>
        table { width: 100%; }

        th {
            height: 50px;
            background-color: #4CAF50;
            color: white;
        }

        th, td {
            border-bottom: 1px solid #ddd;
            padding: 5px;
            text-align: left;
        }

        tr:hover { background-color: #f5f5f5 }

        /*tr:nth-child(even) {*/
        /*background-color: #f2f2f2*/
        /*}*/
    </style>
</head>
<body>
<%--忘了给useBean加scope了，怪不得一直找不到bean…--%>
<%--<jsp:useBean id="person" class="model.bean.foo.Employee" scope="request">--%>
<%--<jsp:setProperty name="person" property="name" value="Fred"/>--%>
<%--<jsp:setProperty name="person" property="name" param="name"/>--%>
<%--</jsp:useBean>--%>
<%--<jsp:getProperty name="person" property="name"/>--%>
<jsp:include page="navbar.jsp"/>
<div class="container">
    Name is:
    <%--${person.name}--%>
    ${user.name}
    <%--Host: ${header.host}<br>--%>
    <%--遍历header这个EL隐含变量： JSTL与EL真是绝配！--%>
    <table>
        <tr>
            <th>Header</th>
            <th>Value</th>
        </tr>
        <c:forEach var="elem" items="${header}">
            <tr>
                <td>${elem.key}</td>
                <td>${elem.value}</td>
            </tr>
        </c:forEach>

        <c:forEach var="elem" items="${param}">
            <tr>
                <td>${elem.key}</td>
                <td>${elem.value}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
