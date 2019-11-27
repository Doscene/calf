<%--suppress HtmlUnknownTarget --%>
<%--
  User: doscene
  Date: 2019/10/13
  Time: 11:20
--%>
<jsp:useBean id="now" class="java.util.Date"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>系统字典-新增</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/AdminLTE.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/skins/skin-blue.css">
    <link rel="stylesheet" href="static/plugins/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/select2/select2.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/plugins/My97DatePicker/WdatePicker.js"></script>
    <style>
        .form-table {
            border-collapse:collapse;
            width: 100%;
        }

        .form-table td, th {
            border: 5px solid white;
            display: table-cell;
            vertical-align: middle;
        }

        .form-table .header-input {
            background-color: #30bbbb;
            color: white;
            text-align: right;
            max-width: 30px;
            padding: 5px;
            width: 10%;
        }

        .form-table .body-input {
            padding-left: 5px;
        }
    </style>
</head>
<body>
<div class="box">
    <form id="code-save-form" autocomplete="off">
        <div class="box-header with-border">
            <h3 class="box-title">系统字典-新增</h3>
            <div class="pull-right">
                <button type="button" class="btn btn-default btn-flat  btn-sm"
                        onclick="submitFrmCode()"><i class="fa fa-save text-aqua"></i>&nbsp;保存
                </button>
                <button type="reset" class="btn btn-default btn-flat  btn-sm"><i class="fa fa-reply text-yellow"></i>&nbsp;重置
                </button>
                <button type="button" onclick="window.close()" class="btn btn-flat btn-default btn-sm"><i
                        class="fa fa-close text-red"></i>&nbsp;关闭
                </button>
            </div>
        </div>
        <div class="box-body">
            <table class="no-padding no-margin form-table">
                <tr>
                    <td class="header-input">字典标识</td>
                    <td class="body-input"><input type="text"></td>
                    <td class="header-input">字典名称</td>
                    <td class="body-input"><input type="text"></td>
                    <td class="header-input">字典值</td>
                    <td class="body-input"><input type="text"></td>
                </tr>
                <tr>
                    <td class="header-input">字典标识</td>
                    <td class="body-input"><input type="text"></td>
                    <td class="header-input">字典名称</td>
                    <td class="body-input"><input type="text"></td>
                    <td class="header-input">字典值</td>
                    <td class="body-input"><input type="text"></td>
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
    $(
        function () {
            //将所有表单元素设置为readonly
            $('form[readonly]').find('input,select').attr('readonly', 'readonly');
            $('form[disabled]').find('input,select').attr('disabled', 'disabled');
        }
    );
    $(function () {
        var temp = now();
        console.log(temp.getFullYear());
        temp.setFullYear(now().getFullYear() + 100);
        var EXPIRED_TIME_DEFAULT = temp;
        $('#expiredTime').val(timeFormat(EXPIRED_TIME_DEFAULT));
    });


    function submitFrmCode() {
        $.post({
            url: 'frm/code/saveSysPermission',
            dataType: 'json',
            data: $('#code-save-form').serialize()
        }).then(function (data) {
            if (data.status === 0) {
                new Promise(function () {
                    alert('添加成功');
                }).then(function (value) {
                    closeWindow()
                });
            } else {
                alert('添加失败:' + data.msg);
            }
        }, function () {
            alert('网络异常');
        });
    }
</script>
</body>
</html>
