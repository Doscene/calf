<%--suppress HtmlUnknownTarget --%>
<%--
  User: doscene
  Date: 2019/10/13
  Time: 11:20
--%>
<jsp:useBean id="now" class="java.util.Date"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fns" uri="https://github.com.doscene/calf/tld/fns" %>
<%@ taglib prefix="calf" uri="https://github.com.doscene/calf/tld/calf" %>
<html>
<head>
    <title>资源管理-新增</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/AdminLTE.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/skins/skin-blue.css">
    <link rel="stylesheet" href="static/plugins/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/select2/select2.css">
    <style>
        * {
            font-size: 12px;
        }

        .form-table {
            border-collapse: collapse;
            width: 100%;

        }

        .form-table td, th {
            border: 1px solid #eeeeee;
            display: table-cell;
            vertical-align: middle;
            font-size: 12px;
        }

        .form-table .header-input {
            color: black;
            text-align: right;
            max-width: 30px;
            padding: 10px 5px;
            width: 10%;
            font-weight:bold;
        }

        .form-table .body-input {
            padding-left: 5px;
        }
    </style>
</head>
<body>
<div class="box">
    <form id="resource-save-form" autocomplete="off">
        <div class="box-header with-border">
            <h3 class="box-title">资源管理-新增</h3>
            <div class="pull-right">
                <button type="button" class="btn btn-default btn-flat  btn-sm"
                        onclick="submitSysResource()"><i class="fa fa-save text-aqua"></i>&nbsp;保存
                </button>
                <button type="reset" class="btn btn-default btn-flat  btn-sm"><i class="fa fa-reply text-yellow"></i>&nbsp;重置
                </button>
                <button type="button" onclick="closeWindow()" class="btn btn-flat btn-default btn-sm"><i
                        class="fa fa-close text-red"></i>&nbsp;关闭
                </button>
            </div>
        </div>
        <div class="box-body">
            <table class="no-padding no-margin form-table">
                <tr>
                    <td class="header-input">资源名称</td>
                    <td class="body-input"><input type="text" class="form-control input-sm" name="resourceName" id="resource_name"></td>
                    <td class="header-input">资源标识</td>
                    <td class="body-input"><input type="text" class="form-control input-sm" name="resourceToken" id="resource_token"></td>
                    <td class="header-input">过期时间</td>
                    <% now.setYear(now.getYear() + 100); %>
                    <td class="body-input"><input type="text" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${now}" />'onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'twoer'})" class="form-control input-sm" name="expiredTime" id="expiredTime"></td>
                </tr>
                <tr>
                    <td class="header-input">资源内容</td>
                    <td class="body-input" colspan="3"><textarea style="resize: none" id="content" name="content"  class="form-control input-sm" rows="4"></textarea></td>
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
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/plugins/My97DatePicker/WdatePicker.js"></script>
<script>
    function submitSysResource() {
        $('.input-group').removeClass('has-error');
        $('input').attr('title', '');
        /* var $loginName = $('#loginName');
         var loginName = $loginName.val();
         //6-12位以数字字母下划线组成且只能字母开头
         if (!loginName || loginName === '' || !/^[a-zA-Z][a-zA-Z\d]{4,11}$/.test(loginName)) {
             $loginName.closest('.input-group').addClass('has-error');
             $loginName.attr('title', '登录名是6-12位以数字字母下划线组成且只能字母开头');
             return false;
         }*/
        if (confirm("确认提交？")) {
            $.post({
                url: 'sys/resource/saveResource',
                dataType: 'json',
                data: $('#resource-save-form').serialize()
            }).then(function (data) {
                if (data.status === 0) {
                    alert('添加成功');
                } else {
                    alert('添加失败:' + data.msg);
                }
            }, function () {
                alert('网络异常');
            });
        }
    }

    function closeWindow() {
        window.opener.dataTable.ajax.reload();
        window.close();
    }
</script>
</body>
</html>
