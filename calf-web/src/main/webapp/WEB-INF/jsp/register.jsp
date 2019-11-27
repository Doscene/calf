<%--
  User: doscene
  Date: 2019/10/10
  Time: 20:41
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>注册</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="static/plugins/layui-2.5.4/css/layui.css" type="text/css">
</head>
<body>
<div class="layui-container" style="width: 600px;">
    <form class="layui-form layui-form-pane" method="post" autocomplete="off">
        <div class="layui-form-item">
            <label class="layui-form-label">登录名称</label>
            <div class="layui-input-block">
                <input type="text" name="loginName" placeholder="登录名称" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">昵称</label>
            <div class="layui-input-block">
                <input type="text" name="tinyName" placeholder="昵称" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">真实姓名</label>
            <div class="layui-input-block">
                <input type="text" name="trueName" placeholder="真实姓名" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">邮箱</label>
            <div class="layui-input-block">
                <input type="text" name="email" placeholder="邮箱" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">电话</label>
            <div class="layui-input-block">
                <input type="text" name="phone" placeholder="电话" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">性别</label>
            <div class="layui-input-block">
                <input type="radio" name="gender" value="1" title="男" checked>
                <input type="radio" name="gender" value="0" title="女">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">年龄</label>
            <div class="layui-input-block">
                <input type="text" name="age" placeholder="年龄" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">生日</label>
            <div class="layui-input-block">
                <input type="text" id="birthday" name="birthday" placeholder="生日" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">身份证</label>
            <div class="layui-input-block">
                <input type="text" name="idCard" placeholder="身份证" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">头像</label>
            <div class="layui-input-block">
                <input type="text" name="headPortraitUrl" placeholder="头像" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">组织机构</label>
            <div class="layui-input-block">
                <input type="text" name="organizationId" placeholder="组织机构" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="register">立即提交</button>
                <button type="button" onclick="history.back()" class="layui-btn layui-btn-primary">返回
                </button>
            </div>
        </div>
    </form>
</div>
<table class="layui-hide" id="choose"></table>
<!--<div id="organization-choose" hidden>
    <div class="container-fluid" id="organization-table-tool-bar">
        <form method="get" class="form-inline form-group-sm" autocomplete="off">
            <div class="form-group">
                <label for="organization-name">组织名称：</label>
                <input id="organization-name" name="organizationName"
                       type="text"
                       class="form-control" placeholder="组织名称">
            </div>
            <div class="form-group">
                <label for="certificate">组织代码证：</label>
                <input id="certificate" name="certificate" type="text"
                       class="form-control"
                       placeholder="组织代码证">
            </div>
            <div class="form-group">
                <label for="principal">负责人：</label>
                <input id="principal" name="principal" type="text"
                       class="form-control"
                       placeholder="负责人">
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default btn-sm" onclick="tb.bootstrapTable('refresh')">查询</button>
            </div>
        </form>
    </div>
<table id="organization-table"></table>
</div>-->
<script src="static/plugins/jquery-2.2.4.js"></script>
<script src="static/plugins/layui-2.5.4/layui.js"></script>
<script>
    layui.use(['form', 'laydate'], function () {
        var form = layui.form, laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#birthday',
            value: new Date()
        });
        //监听提交
        form.on('submit(register)', function (data) {
            $.post({
                url: 'login/register',
                data: data.field,
                dataType: 'json'
            }).then(function (res) {
                if (res['status'] === 0) {
                    layer.msg('注册成功', {
                        icon: 1,
                        time: 1000
                    });
                } else {
                    layer.msg('注册失败（原因：【' + res['msg'] + '】）！', {
                        icon: 5,
                        time: 1000
                    });
                }
            }, function () {
                layer.msg('网络异常！', {
                    icon: 2,
                    time: 1000
                });
            });
            return false;
        });
    });
</script>
</body>
</html>
