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
    <title>资源管理-编辑</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/AdminLTE.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/skins/skin-blue.css">
    <link rel="stylesheet" href="static/plugins/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/select2/select2.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/My97DatePicker/WdatePicker.js"></script>
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
    <form id="resource-edit-form" autocomplete="off">
        <div class="box-header with-border">
            <h3 class="box-title">资源管理-编辑</h3>
            <div class="pull-right">
                <button type="button" class="btn btn-default btn-flat  btn-sm"
                        onclick="submitSysResource()"><i class="fa fa-save text-aqua"></i>&nbsp;保存
                </button>
                <button type="reset" class="btn btn-default btn-flat  btn-sm"><i class="fa fa-reply text-yellow"></i>&nbsp;重置
                </button>
                <button type="button" onclick="window.close()" class="btn btn-flat btn-default btn-sm"><i
                        class="fa fa-close text-red"></i>&nbsp;关闭
                </button>
            </div>
        </div>
        <div class="box-body">
            <input type="hidden" name="pid" value="${resource.pid}">
            <table class="no-padding no-margin form-table">
                <tr>
                    <td class="header-input">资源名称</td>
                    <td class="body-input"><input type="text" class="form-control input-sm" value="${resource.resourceName}" name="resourceName" id="resourceName"></td>
                    <td class="header-input">资源标识</td>
                    <td class="body-input"><input type="text" class="form-control input-sm" name="resourceToken" id="resourceToken" value="${resource.resourceToken}"></td>
                    <td class="header-input">过期时间</td>
                    <td class="body-input"><input type="text" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${resource.expiredTime}" />' onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'twoer'})" class="form-control input-sm" name="expiredTime" id="expiredTime"></td>
                </tr>
                <tr>
                    <td class="header-input">资源内容</td>
                    <td class="body-input" colspan="3"><textarea id="content" name="content" style="resize: none"  class="form-control input-sm" rows="4">${resource.content}</textarea></td>
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
    $(function () {
            //将所有表单元素设置为readonly
            $('form[readonly]').find('input,select').attr('readonly', 'readonly');
            $('form[disabled]').find('input,select').attr('disabled', 'disabled');
        }
    );

    function submitSysResource() {
        $('.input-group').removeClass('has-error');
        $('input').attr('title', '');
        if (confirm("确认修改资源【${resource.resourceName}】？")) {
            $.post({
                url: 'sys/resource/editResource',
                dataType: 'json',
                data: $('#resource-edit-form').serialize()
            }).then(function (data) {
                if (data.status === 0) {
                    alert('修改成功');
                } else {
                    alert('修改失败【'+data.msg+'】');
                }
            }, function () {
                alert('网络异常');
            });
        }
    }
</script>
</body>
</html>
