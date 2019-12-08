<%--suppress HtmlUnknownTarget --%>
<%--
  User: doscene
  Date: 2019/10/10
  Time: 20:43
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="calf" uri="https://github.com.doscene/calf/tld/calf" %>
<%@ taglib prefix="fns" uri="https://github.com.doscene/calf/tld/fns" %>
<html>
<head>
    <title>权限管理-列表</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/AdminLTE.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/skins/skin-blue.css">
    <link rel="stylesheet" href="static/plugins/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="static/plugins/zTree/css/metroStyle/metroStyle.css">
    <link rel="stylesheet" href="static/plugins/calf/common/common.css">
</head>
<body>
<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <form id="permission-list-form" class="form-inline no-padding no-margin" role="form">
                    <div class="pull-left">
                        <input type="hidden" name="parentId" value="${param.parentId}"/>
                        <div class="form-group">
                            <label for="code-token">权限标识</label>
                            <input type="text" name="codeToken" class="form-control input-sm" id="code-token">
                        </div>
                        <div class="form-group">
                            <label for="code-name">权限名称</label>
                            <input type="text" class="form-control input-sm" name="codeName" id="code-name">
                        </div>
                    </div>
                    <div class="pull-right">
                        <div class="form-group">
                            <button type="button" class="btn btn-default btn-flat  btn-sm"
                                    onclick=""><i class="fa fa-search text-aqua"></i>&nbsp;查询
                            </button>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-default btn-flat  btn-sm"
                                    onclick="saveSysPermission()"><i class="fa fa-plus text-green"></i>&nbsp;新增
                            </button>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-default btn-flat  btn-sm"
                                    onclick="editSysPermission()"><i class="fa fa-edit text-yellow"></i>&nbsp;编辑
                            </button>
                        </div>
                        <div class="form-group">
                            <button onclick="window.close()" type="button" class="btn btn-flat btn-default btn-sm"><i
                                    class="fa fa-close text-red"></i>&nbsp;删除
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="box-body no-padding">
                <ul id="sys-permission-tree" class="ztree col-sm-4"></ul>
                <div class="col-sm-8">
                    <form id="permission-edit" role="form" autocomplete="off" readonly disabled>
                        <input name="pid" type="hidden" id="pid">
                        <table class="no-padding no-margin form-table">
                            <tr>
                                <td class="header-input">权限标识</td>
                                <td class="body-input"><input type="text" class="form-control input-sm"
                                                              name="permissionToken" id="permissionToken"></td>
                                <td class="header-input">权限名称</td>
                                <td class="body-input"><input type="text" class="form-control input-sm"
                                                              name="permissionName" id="permissionName"></td>
                            </tr>
                            <tr>
                                <td class="header-input">父级节点</td>
                                <td class="body-input"><input type="text" class="form-control input-sm" name="parentId"
                                                              id="parentId"></td>
                                <td class="header-input">关联资源</td>
                                <td class="body-input"><input type="text" class="form-control input-sm" name="resourceId"
                                                              id="resourceId"></td>
                            </tr>
                            <tr>
                                <td class="header-input">过期时间</td>
                                <td class="body-input"><input type="text" class="form-control input-sm"
                                                              onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'twoer'})"
                                                              name="expiredTime" id="expiredTime"></td>
                                <td class="header-input">关联操作</td>
                                <td class="body-input"><input type="text" class="form-control input-sm"
                                                              name="operationId" id="operationId"></td>
                            </tr>
                            <tr>
                                <td class="header-input">权限类型</td>
                                <td class="body-input"><calf:select id="permissionType" name="permissionType"
                                                                    headerText="---请选择---"
                                                                    collection="${fns:getSubCodes('PERMISSION_TYPE')}"
                                                                    textName="codeName"
                                                                    className="form-control input-sm select2"
                                                                    valueName="codeText"/></td>
                                <td class="header-input">树类型</td>
                                <td class="body-input"><calf:select id="treeType" name="treeType" headerText="---请选择---"
                                                                    collection="${fns:getSubCodes('TREE_TYPE')}"
                                                                    textName="codeName"
                                                                    className="form-control input-sm select2"
                                                                    valueName="codeText"/></td>
                            </tr>
                            <tr>
                                <td class="header-input">排序</td>
                                <td class="body-input">
                                    <input type="text" name="sort" id="sort">
                                </td>
                                <td colspan="4"></td>
                            </tr>
                        </table>
                        <div class="form-group no-padding form-inline" id="permission-commit-group"
                             style="margin-top: 10px">
                            <button onclick="submitSysPermission()" type="button"
                                    class="btn btn-default btn-flat  btn-sm">
                                <i class="fa fa-check text-green"></i>&nbsp;&nbsp;提交
                            </button>
                            <button onclick="beforeCommit()" type="button" class="btn btn-default btn-flat  btn-sm">
                                <i class="fa fa-reply text-yellow"></i>&nbsp;&nbsp;取消
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="static/plugins/jquery-2.2.4.js"></script>
<script src="static/plugins/calf/common/common.js"></script>
<script src="static/plugins/calf/common/common.jquery.js"></script>
<script src="static/plugins/zTree/js/jquery.ztree.all.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/plugins/My97DatePicker/WdatePicker.js"></script>
<script>
    var settings = {
        data: {
            key: {
                checked: "checked",
                children: "children",
                isParent: "isParent",
                isHidden: "hidden",
                name: "name",
                title: "title",
                url: "url"
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode) {
                $.get(
                    {
                        url: 'sys/permission/permission',
                        dataType: 'json',
                        data: {pid: treeNode['id']}
                    }
                ).then(function (value) {
                    for (var field in value['data']) {
                        $('#permission-edit [name="' + field + '"]').val(value['data'][field]);
                    }
                    $('#expiredTime').val(timeFormat(new Date(value['data']['expiredTime'])));
                }, function (reason) {
                    alert('网络异常')
                });
            }
        },
        check: {
            /* enable: true,  //true 、 false 分别表示 显示 、不显示 复选框或单选框*/
            nocheckInherit: true  //当父节点设置 nocheck = true 时，设置子节点是否自动继承 nocheck = true
        },
        async: {
            url: 'sys/permission/permissionList',
            enable: true,
            type: 'get',
            dataType: 'json',
            autoParam: ["id=parentId"],
            dataFilter: function (treeId, parentNode, childNodes) {
                var node = [];
                $(childNodes['data']).each(function () {
                    node.push({
                        id: this['pid'],
                        name: this['permissionName'],
                        open: false,
                        isParent: this['treeType'] === '1',
                        parentId: this['parentId']
                    });
                });
                return node;
            }
        }
    };
    var $zTreeObj = $.fn.zTree.init($("#sys-permission-tree"), settings); //初始化树
    var $sysPermissionEditForm = $('#permission-edit');
    var $permissionCommitGroup = $('#permission-commit-group');
    $permissionCommitGroup.hide();
    $sysPermissionEditForm.lock();
    var editUrl;
    var alertText;

    function editSysPermission() {
        var pid = $('#pid').val();
        if (pid && pid.length > 0) {
            $permissionCommitGroup.show();
            $sysPermissionEditForm.unLock();
            editUrl = 'sys/permission/editPermission';
            alertText = '修改';
        } else {
            alert('请选择一个节点');
        }
    }

    function saveSysPermission() {
        var pid = $('#pid').val();
        $permissionCommitGroup.show();
        $sysPermissionEditForm.reset();
        $sysPermissionEditForm.unLock();
        editUrl = 'sys/permission/savePermission';
        alertText = '添加';
        $('#parentId').val(pid);
    }

    function submitSysPermission() {
        $.post({
            url: editUrl,
            dataType: 'json',
            async: false,
            data: $sysPermissionEditForm.serialize()
        }).then(function (data) {
            if (data.status === 0) {
                alert(alertText + '成功');
            } else {
                alert(alertText + '失败:' + data.msg);
            }
        }, function () {
            alert('网络异常');
        });
        beforeCommit();
    }

    function beforeCommit() {
        $permissionCommitGroup.hide();
        $sysPermissionEditForm.lock();
        $sysPermissionEditForm.reset();
        alertText = undefined;
        editUrl = undefined;
    }

    function deleteFrmCode() {

    }
</script>
</body>
</html>
