<%--
  Created by IntelliJ IDEA.
  User: Nigel
  Date: 2016/4/11
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/lib/css/bootstrap.min.css">

<style>
    body {
        padding-top: 20px;
        padding-bottom: 20px;
        /* Margin bottom by footer height */
        margin-bottom: 70px;
    }
</style>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Fantastic Show</a>
            <%--响应式导航栏，当屏幕宽度不够时，.collapse .navbar-collapse的导航栏会隐藏，显示这个触发按钮--%>
            <button class="navbar-toggle" type="button" data-toggle="collapse"
                    data-target="#collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="navbar-collapse collapse" id="collapse">
            <ul class="nav navbar-nav">
                <li class="active" id="home"><a href="/home">主页</a></li>
                <li><a href="/selfSpace">个人空间</a></li>
                <li><a href="/jsp/about.jsp">关于</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <img src="/avatar" alt="头像" class="img-circle" id="img" width="30" style="padding-top: 10px">
                </li>
                <li id="sideBtn" class="dropdown">
                    <c:choose>
                        <c:when test="${sessionScope.login}">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    ${sessionScope.user.nickname} <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="/logoff" id="logoff">退出</a></li>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <a href="/login">登录
                                <span class="glyphicon glyphicon-arrow-right"></span></a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </div>
    </div>
</nav>
<script src="/lib/js/jquery-2.2.3.js"></script>
<script src="/lib/js/bootstrap.js"></script>
<script>
    $(function () {
        <%--$("li").eq(<c:out value="${param.page}" default="0"/>).addClass("active");--%>

        $("a[href='" + window.location.pathname + "']").parent().addClass("active").siblings().removeClass("active");

        $("#logoff").click(function () {
            $.getJSON("/logoff", function (data) {
                if (data.success.length > 0) {
                    //改变src属性之后会重新请求图片，如何重新请求同一URL上的图片 1.增加无意义的参数
                    $("#img").attr("src", "/avatar?random=1");
                    $("#sideBtn").html(" <a href=\"/login\">登录 <span class=\" glyphicon glyphicon-arrow-right\"></span></a>");
                }
            });
            return false;
        });

        //鼠标滑入滑出效果
        $("#sideBtn").hover(function () {
            $(this).addClass("open");
        }, function () {
            $(this).removeClass("open");
        });
    });
</script>
