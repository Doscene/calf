<%--suppress HtmlUnknownTarget --%>
<%--
  User: doscene
  Date: 2019/10/10
  Time: 19:01
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fns" uri="https://github.com.doscene/calf/tld/fns" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CALF</title>
    <base href="${pageContext.request.contextPath}/">
    <!--<link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.min.css">-->
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <style>
        #login-panel {
            margin-top: 30%;
        }

        #login-panel .panel-body {
            padding-left: 10px;
            padding-right: 10px;;
            padding-bottom: 0;
        }

        #login-panel .panel-heading {
            padding-left: 10px;
            padding-right: 10px;
        }

        html, body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            border: 0;
        }

        .login-error {
            color: red;
            font-size: 13px;
            line-height: 16px;
            float: right
        }

        .btn, .panel, .panel-body, .panel-heading, .panel-title {
            border-radius: 0;
        }
    </style>
</head>
<body> <!--style="background: url(static/images/loginbg.jpg) no-repeat center;"-->


<div class="container-fluid" style="height: 100%;padding: 0">
    <div class="col-sm-offset-7 col-sm-4">
        <div class="panel panel-default" id="login-panel">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <span>登录</span>
                    <span class="login-error">${requestScope.shiroLoginFailure}</span>
                </h4>
            </div>
            <div class="panel-body">
                <form id="login-form" action="login" onsubmit="return login();" method="post" class="form-horizontal">
                    <div class="form-group form-group-sm has-feedback">
                        <label for="username" class="col-sm-3 control-label">帐号：</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="username" id="username" placeholder="帐号">
                        </div>
                    </div>
                    <div class="form-group form-group-sm has-feedback">
                        <label for="password" class="col-sm-3 control-label">密码：</label>
                        <div class="col-sm-9">
                            <input type="password" class="form-control" name="password" id="password" placeholder="密码">
                        </div>
                    </div>
                    <div class="form-group form-group-sm has-feedback">
                        <label for="captcha" class="col-sm-3 control-label">验证码：</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="captcha" id="captcha" placeholder="验证码">
                        </div>
                        <div class="col-sm-4" style="padding-left: 0">
                            <img src="login/captcha" onclick="this.setAttribute('src','login/captcha')"
                                 style="height: 30px;width: 100%"
                                 alt="验证码">
                        </div>
                    </div>
                    <div class="form-group form-group-sm">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" class="btn btn-primary btn-sm col-sm-5">登录</button>
                            <button type="reset" class="btn btn-default btn-sm col-sm-offset-2  col-sm-5">重置
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="text-center" style="position: absolute;bottom: 0;text-align: center;width: 100%;padding: 20px">
        <span style="padding-left: 10px;padding-right: 10px">版本号：${fns:getSubCodeByToken('SYSTEM_VERSION')}</span>
        <span style="padding-left: 10px;padding-right: 10px">发布日期：${fns:getSubCodeByToken('SYSTEM_PUBLISH_TIME')}</span>
    </div>
</div>
<script src="static/plugins/jquery-2.2.4.js"></script>
<script src="static/plugins/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script>
    <shiro:user>
    location.href = "index";
    </shiro:user>
    var $username = $('#username');
    var $password = $('#password');
    var $loginError = $('.login-error');

    function login() {
        $('.form-group').removeClass('has-error');
        var username = $username.val();
        //6-12位以数字字母下划线组成且只能字母开头
        if (!username || username === '' || !/^[a-zA-Z][a-zA-Z\d]{4,11}$/.test(username)) {
            $username.closest('.form-group').addClass('has-error');
            $loginError.text('账号不符合规则');
            return false;
        }
        var password = $password.val();
        //6-20位以数字字母下划线组成且不能由下划线开头
        if (!password || password === '' || !/^[a-zA-Z\d][a-zA-Z\d_]{5,19}$/.test(password)) {
            $password.closest('.form-group').addClass('has-error');
            $loginError.text('密码不符合规则');
            return false;
        }
        $('[data-toggle="popover"]').popover();
        return true;
    }
</script>
</body>
</html>
