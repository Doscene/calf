<%--suppress HtmlUnknownTarget --%>
<%--
  User: doscene
  Date: 2019/10/13
  Time: 11:20
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="https://github.com.doscene/calf/tld/fns" %>
<%@ taglib prefix="calf" uri="https://github.com.doscene/calf/tld/calf" %>
<html>
<head>
    <title>用户管理-编辑</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/AdminLTE.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/skins/skin-blue.css">
    <link rel="stylesheet" href="static/plugins/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/select2/select2.css">
    <link rel="stylesheet" href="static/plugins/calf/common/common.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="box">
    <form id="user-edit-form" autocomplete="off">
        <div class="box-header with-border">
            <h3 class="box-title">用户管理-编辑</h3>
            <div class="pull-right">
                <button type="button" class="btn btn-default btn-flat  btn-sm"
                        onclick="submitSysUser()"><i class="fa fa-save text-aqua"></i>&nbsp;保存
                </button>
                <button type="reset" class="btn btn-default btn-flat  btn-sm"><i class="fa fa-reply text-yellow"></i>&nbsp;重置
                </button>
                <button type="button" onclick="window.close()" class="btn btn-flat btn-default btn-sm"><i
                        class="fa fa-close text-red"></i>&nbsp;关闭
                </button>
            </div>
        </div>
        <div class="box-body">
            <input type="hidden" name="${sysUser.pid}" id="pid">
            <table class="no-padding no-margin form-table">
                <tr>
                    <td class="header-input">登录名</td>
                    <td class="body-input"><input type="text" class="form-control input-sm" readonly
                                                  value="${sysUser.loginName}" name="loginName" id="loginName"></td>
                    <td class="header-input">姓名</td>
                    <td class="body-input"><input type="text" class="form-control input-sm" value="${sysUser.trueName}"
                                                  name="trueName" id="trueName"></td>
                    <td class="header-input">年龄</td>
                    <td class="body-input"><input type="text" class="form-control input-sm" value="${sysUser.age}"
                                                  name="age" id="age"></td>
                </tr>
                <tr>
                    <td class="header-input">邮箱</td>
                    <td class="body-input"><input type="text" class="form-control input-sm" value="${sysUser.email}"
                                                  name="email" id="email"></td>
                    <td class="header-input">电话号码</td>
                    <td class="body-input"><input type="text" class="form-control input-sm" value="${sysUser.phone}"
                                                  name="phone" id="phone"></td>
                    <td class="header-input">性别</td>
                    <td class="body-input"><calf:select id="gender" name="gender" headerText="---请选择---"
                                                        selected="${sysUser.gender}"
                                                        collection="${fns:getSubCodes('GENDER_TYPE')}"
                                                        textName="codeName" className="form-control input-sm select2"
                                                        valueName="codeText"/></td>
                </tr>
                <tr>
                    <td class="header-input">生日</td>
                    <td class="body-input"><input type="text" class="form-control input-sm"
                                                  value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sysUser.birthday}" />"
                                                  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'twoer'})"
                                                  name="birthday"
                                                  id="birthday"></td>
                    <td class="header-input">过期时间</td>
                    <td class="body-input"><input type="text"
                                                  value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sysUser.expiredTime}" />'
                                                  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'twoer'})"
                                                  class="form-control input-sm" name="expiredTime" id="expiredTime">
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<script src="static/plugins/jquery-2.2.4.js"></script>
<script src="static/plugins/calf/common/common.js"></script>
<script src="static/plugins/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="static/plugins/AdminLTE-2.3.0/plugins/select2/select2.js"></script>
<script src="static/plugins/AdminLTE-2.3.0/plugins/select2/i18n/zh-CN.js"></script>
<script>
    function submitSysUser() {
        $('.input-group').removeClass('has-error');
        $('input').attr('title', '');
        var $loginName = $('#loginName');
        var loginName = $loginName.val();
        //6-12位以数字字母下划线组成且只能字母开头
        if (!loginName || loginName === '' || !/^[a-zA-Z][a-zA-Z\d]{4,11}$/.test(loginName)) {
            $loginName.closest('.input-group').addClass('has-error');
            $loginName.attr('title', '登录名是6-12位以数字字母下划线组成且只能字母开头');
            return false;
        }
        if (confirm("确认修改用户【${sysUser.loginName}】？")) {
            $.post({
                url: 'sys/user/userEdit',
                dataType: 'json',
                data: $('#user-edit-form').serialize()
            }).then(function (data) {
                if (data.status === 0) {
                    alert('修改成功');
                } else {
                    alert('修改失败');
                }
            }, function () {
                alert('网络异常');
            });
        }
    }
</script>
</body>
</html>
