<%--
  Created by IntelliJ IDEA.
  User: Nigel
  Date: 2016/4/11
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Fantastic Show</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/lib/css/bootstrap.min.css">
    <link rel="stylesheet" href="/lib/css/fileinput.css">
</head>

<body>
<div class="container">
    <%@ include file="navbar.jsp" %>

    <form method="post" action="/home" enctype="multipart/form-data">
        <label class="control-label">选择图片</label>
        <input type="file" name="image" id="image" class="file" multiple>
        <label for="option"></label>
        <select name="option" id="option" class="form-control">
            <option value="combinePic">合成</option>
            <option value="toBlackAndWhite" selected>黑白照</option>
            <option value="rotateLeft">向左翻转</option>
            <option value="rotateRight">向右翻转</option>
            <option value="rotate180">翻转180度</option>
        </select>
    </form>

    <%@ include file="footer.jsp" %>
</div>

<script src="/lib/bootstrap-fileinput/js/fileinput.js"></script>
<script src="/lib/bootstrap-fileinput/js/fileinput_locale_zh.js"></script>
<%--加载了两次bootstrap.js会出错--%>
<%--<script src="/lib/js/bootstrap.js"></script>--%>
<script>
    $('#image').fileinput({
        //uploadAsync: true,
        showPreview: true,
        //caption就是选中文件之后显示文件名的长条框
        showCaption: false,
        showRemove: false,
        showUpload: true,
        //        showCancel: false,
        dropZoneEnabled: false,
        minFileCount: 1,
        maxFileCount: 2,
        autoReplace: true,
        allowedFileTypes: ['image'],
        language: 'zh',
        uploadUrl: '/home',
        uploadExtraData: function () {
            return {option: $("#option").val()};
        }
    });
    //    $("body").height($(window).height);
</script>
</body>
</html>
