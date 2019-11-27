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
    <title>工作流模型-新增</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/AdminLTE.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/skins/skin-blue.css">
    <link rel="stylesheet" href="static/plugins/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/select2/select2.css">
</head>
<body>
<div class="box">
    <form id="workflow-model-save-form" autocomplete="off">
        <div class="box-header with-border">
            <h3 class="box-title">工作流模型-新增</h3>
            <div class="pull-right">
                <button type="button" class="btn btn-default btn-flat  btn-sm"
                        onclick="submitSysUser()"><i class="fa fa-save text-aqua"></i>&nbsp;保存
                </button>
                <button type="reset" class="btn btn-default btn-flat  btn-sm"><i class="fa fa-reply text-yellow"></i>&nbsp;重置
                </button>
                <button type="button" onclick="closeWindow()" class="btn btn-flat btn-default btn-sm"><i
                        class="fa fa-close text-red"></i>&nbsp;关闭
                </button>
            </div>
        </div>
        <div class="box-body">
            <div class="form-group no-padding form-inline">
                <div class="input-group col-sm-3">
                    <label for="name" class="input-group-addon">
                        名称
                    </label>
                    <input type="text" class="form-control input-sm" name="name" id="name">
                </div>
                <div class="input-group col-sm-offset-1 col-sm-3">
                    <label for="key" class="input-group-addon">
                        键（KEY）
                    </label>
                    <input type="text" class="form-control input-sm" name="key" id="key">
                </div>
                <div class="input-group col-sm-offset-1 col-sm-3">
                    <label for="description" class="input-group-addon">
                        描述
                    </label>
                    <input type="text" class="form-control input-sm" name="description" id="description">
                </div>
            </div>
            <div class="form-group no-padding form-inline">
                <div class="input-group col-sm-3">
                    <label for="namespace" class="input-group-addon">
                        命名空间
                    </label>
                    <input type="text" class="form-control input-sm" name="namespace" id="namespace"
                           value="http://b3mn.org/stencilset/bpmn2.0#">
                </div>
            </div>
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
    function submitSysUser() {
        if (confirm("确认提交？")) {
            $.post({
                url: 'workflow/model/save',
                dataType: 'json',
                data: $('#workflow-model-save-form').serialize()
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
