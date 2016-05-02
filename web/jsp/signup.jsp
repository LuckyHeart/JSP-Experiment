<%--
  Created by IntelliJ IDEA.
  User: Nigel
  Date: 2016/4/19
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>注册</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        label.error {
            color: crimson;
        }
    </style>
</head>

<body>
<div class="container">
    <%@ include file="navbar.jsp" %>

    <div class="alert alert-warning alert-dismissible" style="display:none;">
        <button class="close" type="button" data-dismiss="alert">&times;</button>
        <span id="alert"></span>
    </div>

    <form method="post" id="form" action="/signup">
        <div class="row">
            <div class="col-lg-offset-4 col-lg-4">
                <div class="form-group has-feedback">
                    <%--label加了control-label之后，上面的has-success才会对label起作用--%>
                    <label for="name" class="control-label">用户名</label>
                    <input type="text" name="name" id="name" class="form-control" value="${param.name}"
                           autofocus
                           placeholder="字母与数字的组合" data-valid="false">
                    <span class="glyphicon form-control-feedback"></span>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-offset-4 col-lg-4">
                <div class="form-group has-feedback">
                    <label for="nickname" class="control-label">昵称</label>
                    <input type="text" name="nickname" id="nickname" class="form-control" placeholder="请填写昵称"
                           data-valid="false">
                    <span class="glyphicon form-control-feedback"></span>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-offset-4 col-lg-4">
                <div class="form-group has-feedback">
                    <label for="email" class="control-label">邮箱</label>
                    <input type="email" name="email" id="email" class="form-control onlineCheck" placeholder="请填写有效邮箱"
                           data-valid="false">
                    <span class="glyphicon form-control-feedback"></span>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-offset-4 col-lg-4">
                <div class="form-group has-feedback">
                    <label for="password" class="control-label">密码</label>
                    <input type="password" name="password" id="password" class="form-control" value="${param.password}"
                           placeholder="6位以上字母与数字" data-valid="false">
                    <span class="glyphicon form-control-feedback"></span>
                </div>
            </div>
        </div>

        <%--has-feedback配合后面的form-control-feedback显示反馈信息--%>
        <div class="row">
            <div class="col-lg-offset-4 col-lg-4">
                <div class="form-group has-feedback">
                    <label for="tel">手机号</label>
                    <%--input-group要仅包含输入部分的组件，即要合并在一起的几个部分--%>
                    <div class="input-group">
                        <input type="text" id="tel" name="tel" class="form-control" placeholder="请输入11位手机号码">
                        <span class="input-group-btn"><button type="button" class="btn btn-default"
                                                              id="sendTel" disabled>获取验证码</button></span>
                    </div>
                    <span class="glyphicon form-control-feedback"></span>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-4 col-lg-offset-4">
                <div class="form-group has-feedback">
                    <label for="code">验证码</label>
                    <input type="text" name="code" id="code" class="form-control" disabled placeholder="验证码">
                    <span class="glyphicon form-control-feedback"></span>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-4 col-lg-offset-4">
                <button type="submit" id="submit" class="btn btn-block btn-success" style="margin-top: 20px;">确认注册
                </button>
            </div>
        </div>

        <%--todo 添加头像选择--%>
    </form>

    <%@include file="footer.jsp" %>
</div>

<script src="/lib/js/jquery.validate.js"></script>
<script src="/lib/js/messages_zh.js"></script>
<script>
    $.validator.addMethod("username", function (value, elem) {
        return this.optional(elem) || /^[A-Za-z0-9]+$/.test(value);
    }, "只能包含字母和数字");
    $.validator.addMethod("tel", function (value, elem) {
        return this.optional(elem) || /^1[3|4|5|7|8]\d{9}$/.test(value);
    }, "请输入合法的手机号");

    $("#form").validate({
                errorPlacement: function (error, element) {
                    element.parents(".form-group").append(error);
                },

                rules: {
                    name: {
                        required: true,
                        minlength: 3,
                        remote: "/signup/check",
                        username: true
                    },
                    email: {
                        required: true,
                        email: true,
                        remote: "/signup/check"
                    },
                    nickname: {
                        required: true,
                        minlength: 2
                    },
                    password: {
                        required: true,
                        minlength: 6
                    },
                    code: {
                        remote: "/signup/check",
                        required: true,
                        minlength: 4,
                        maxlength: 4
                    },
                    tel: {
                        tel: true,
                        required: true,
                        minlength: 11,
                        maxlength: 11
                    }
                }
            }
    );

    $("#tel").keyup(function () {
        $("#sendTel").attr("disabled", !$(this).valid());
    });
    //第一次选中输入框进行输入，不会检测keyup，所以这里自己加上
    $("#code").keyup(function () {
        $(this).valid();
    });

    $("#sendTel").click(function () {
        var tel = $("#tel").val();
        var $this = $(this);
        $this.text("发送中...").attr("disabled", true);
        $.getJSON("/sendCode", {tel: tel}, function (data) {
            if (data.alibaba_aliqin_fc_sms_num_send_response.result.success) {
                $this.text("发送成功!");
                $("#code").removeAttr("disabled");
            } else {
                $this.text("发送失败，请稍后重试...");
            }
        });
    });
</script>

</body>
</html>
