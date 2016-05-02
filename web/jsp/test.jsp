<%--
  Created by IntelliJ IDEA.
  User: Nigel
  Date: 2016/4/21
  Time: 22:23
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" href="/lib/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-6 col-md-4 col-center-block">
            <form action="#">
                <h2>请登录</h2>
                <label for="username" class="sr-only">用户名</label>
                <input type="text" id="username" class="form-control" placeholder="用户名" required autofocus>
                <label for="inputPassword" class="sr-only">密码</label>
                <input type="password" id="inputPassword" class="form-control" placeholder="密码" required>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me">记住我
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
            </form>
        </div>
    </div>
</div>
<style>
    .col-center-block {
        float: none;
        display: block;
        margin: 0 auto;
    }
</style>
<script src="/lib/js/jquery-2.2.3.js"></script>
<script src="/lib/js/bootstrap.js"></script>
</body>
</html>
