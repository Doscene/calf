<%--suppress HtmlUnknownTarget --%>
<%--
  User: doscene
  Date: 2019/10/13
  Time: 11:20
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>系统字典-编辑</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/AdminLTE.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/skins/skin-blue.css">
    <link rel="stylesheet" href="static/plugins/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/select2/select2.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/plugins/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="box">
    <form id="code-save-form" autocomplete="off">
        <div class="box-header with-border">
            <h3 class="box-title">系统字典-编辑</h3>
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
            <div class="form-group no-padding form-inline">
                <div class="input-group col-sm-3">
                    <label for="codeToken" class="input-group-addon">
                        字典标识
                    </label>
                    <input type="text" class="form-control input-sm" name="codeToken" id="codeToken"
                           value="${frmCode.codeToken}">
                </div>
                <div class="input-group col-sm-offset-1 col-sm-3">
                    <label for="codeName" class="input-group-addon">
                        字典名称
                    </label>
                    <input type="text" class="form-control input-sm" name="codeName" id="codeName"
                           value="${frmCode.codeName}">
                </div>
                <div class="input-group col-sm-offset-1 col-sm-3">
                    <label for="codeText" class="input-group-addon">
                        字典值&nbsp;&nbsp;&nbsp;&nbsp;
                    </label>
                    <input type="text" class="form-control input-sm" name="codeText" id="codeText"
                           value="${frmCode.codeText}">
                </div>
            </div>
            <div class="form-group form-inline">
                <div class="input-group col-sm-3">
                    <label for="expiredTime" class="input-group-addon">
                        过期时间
                    </label>
                    <input type="text" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
                value="${frmCode.createTime}" />' onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'twoer'})"
                           class="form-control input-sm" name="expiredTime" id="expiredTime">
                </div>
                <div class="input-group col-sm-offset-1 col-sm-3">
                    <label for="codeType" style="min-width: 75px" class="input-group-addon input-sm">
                        字典类型
                    </label>
                    <select class="form-control select2 input-sm" name="codeType" id="codeType">
                        <option value="">---请选择---</option>
                        <option value="0">叶子节点</option>
                        <option value="1">树干节点</option>
                    </select>
                </div>
                <div class="input-group col-sm-offset-1 col-sm-3">
                    <label for="parentId" class="input-group-addon">
                        父级节点
                    </label>
                    <input type="text" class="form-control input-sm" readonly="readonly" disabled="disabled"
                           name="parentId" id="parentId"
                           value="${frmCode.parentId}">
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
<script>
    $(
        function () {
            //将所有表单元素设置为readonly
            $('form[readonly]').find('input,select').attr('readonly', 'readonly');
            $('form[disabled]').find('input,select').attr('disabled', 'disabled');
        }
    );

    function submitFrmCode() {
        $.post({
            url: 'frm/code/saveSysPermission',
            dataType: 'json',
            data: $('#code-save-form').serialize()
        }).then(function (data) {
            if (data.status === 0) {
                alert('添加成功');
                closeWindow()
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
