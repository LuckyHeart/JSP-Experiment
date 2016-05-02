<%--
  Created by IntelliJ IDEA.
  User: Nigel
  Date: 2016/4/18
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
<div class="container">
    <%@ include file="navbar.jsp" %>

    <%--从其他需要先登录的页面跳转来，则显示此警告框--%>
    <div class="alert alert-warning alert-dismissible" style="display:
    ${param.loginFirst ? "block" :"none"};">
        <button type="button" class="close" data-dismiss="alert"><span>&times;</span></button>
        <span class="glyphicon glyphicon-alert"> </span>
        <span id="alert">请先登录或注册</span>
    </div>

    <form id="form" method="post">
        <div class="row">
            <div class="col-lg-offset-4 col-lg-4">
                <div class="form-group">
                    <label for="name">用户名</label>
                    <input type="text" id="name" name="name" class="form-control"
                           value="${param.name ? param.name: cookie.name.value}"
                           placeholder="用户名" autofocus>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-offset-4 col-lg-4">
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" id="password" name="password" class="form-control"
                           value="${param.password ? param.password :cookie.password.value}"
                           placeholder="密码">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-4 col-lg-offset-4">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="useCookie" name="useCookie" value="useCookie" checked="checked">
                        记住密码
                    </label>
                    <span class=" pull-right"><a href="#">忘记密码</a></span>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-4 col-lg-offset-4">
                <div class="btn-group btn-group-justified ">
                    <div class="btn-group btn-group-md">
                        <%--点击注册时，向注册表单jsp POST已填写的部分--%>
                        <button type="submit" formaction="/jsp/signup.jsp" class="btn btn-success">注册</button>
                    </div>
                    <div class="btn-group btn-group-md">
                        <button type="submit" id="submit" class="btn btn-primary">登录</button>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <%@ include file="footer.jsp" %>
</div>
<script>
    $("#submit").click(function () {
        if (check("name") && check("password")) {
            $.ajax({
                method: 'post',
                url: '/login',
                //FormData以multipart/form-data传输，而默认的req.getParameter以application/x-www-url-encoded
                data: new FormData($('#form')[0]),
                dataType: 'json',
                processData: false,
                contentType: false,
                cache: false
            }).fail(function () {
                $("#alert").text("网络错误或服务器出错，请稍后重试").parent().fadeIn();
            }).done(function (data) {
                if (data.success) {
                    window.location.href = "/home";
                } else {
                    console.log(data);
                    $("#alert").text(data.error).parent().remove("alert-warning").addClass("alert-danger").fadeIn();
                    $("#name").focus();
                }
            });
        } else {
            $("#alert").text("请填入用户名和密码").parent().fadeIn();
            $("#name").focus();
        }
        return false;
    });

    function check(id) {
        id = '#' + id;
        var value = $(id).val().trim();
        $(id).val(value);
        return value.length > 0;
    }
</script>

</body>
</html>
