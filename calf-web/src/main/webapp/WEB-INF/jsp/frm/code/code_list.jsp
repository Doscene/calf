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
    <title>系统字典-列表</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/AdminLTE.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/skins/skin-blue.css">
    <link rel="stylesheet" href="static/plugins/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="static/plugins/zTree/css/metroStyle/metroStyle.css">
    <style>
        .form-table {
            border-collapse: collapse;
            width: 100%;
            font-size: 12px;
        }

        .form-table td, th {
            border: 1px solid #eeeeee;
            display: table-cell;
            vertical-align: middle;
        }

        .form-table .header-input {
            color: black;
            text-align: right;
            max-width: 30px;
            padding: 10px 5px;
            width: 15%;
        }

        .form-table .body-input {
            padding-left: 5px;
        }
    </style>
</head>
<body>
<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <form id="code-list-form" class="form-inline no-padding no-margin" role="form">
                    <div class="pull-left">
                        <input type="hidden" name="parentId" value="${param.parentId}"/>
                        <div class="form-group">
                            <label for="code-token">字典标识</label>
                            <input type="text" name="codeToken" class="form-control input-sm" id="code-token">
                        </div>
                        <div class="form-group">
                            <label for="code-name">字典名称</label>
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
                                    onclick="saveFrmCode()"><i class="fa fa-plus text-green"></i>&nbsp;新增
                            </button>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-default btn-flat  btn-sm"
                                    onclick="editFrmCode()"><i class="fa fa-edit text-yellow"></i>&nbsp;编辑
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
                <ul id="frm-code-tree" class="ztree col-sm-4"></ul>
                <div class="col-sm-8">
                    <form id="code-edit" role="form" autocomplete="off" readonly disabled>
                        <input name="pid" type="hidden" id="pid">
                        <table class="no-padding no-margin form-table">
                            <tr>
                                <td class="header-input">字典标识</td>
                                <td class="body-input"><input type="text" name="codeToken" id="codeToken"></td>
                                <td class="header-input">字典名称</td>
                                <td class="body-input"><input type="text" name="codeName"></td>
                            </tr>
                            <tr>
                                <td class="header-input">字典类型</td>
                                <td class="body-input"><input type="text" name="codeType" id="codeType"></td>
                                <td class="header-input">字典名称</td>
                                <td class="body-input"><input type="text" name="codeName"></td>
                             </tr>
                            <tr>
                                <td class="header-input">字典值</td>
                                <td class="body-input"><input type="text" name="codeText" id="codeText"></td>
                                <td class="header-input">树类型</td>
                                <td class="body-input"><calf:select id="treeType" name="treeType" headerText="---请选择---"
                                                 collection="${fns:getSubCodes('TREE_TYPE')}" textName="codeName" valueName="codeText"/></td>
                            </tr>
                            <tr>
                                <td class="header-input">父级节点</td>
                                <td class="body-input"><input type="text" class="" name="parentId" id="parentId"></td>
                                <td class="header-input">过期时间</td>
                                <td class="body-input"><input type="text" class="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'twoer'})" name="expiredTime" id="expiredTime"></td>
                            </tr>
                        </table>
                        <div class="form-group no-padding form-inline" id="code-commit-group">
                            <button onclick="submitFrmCode()" type="button" class="btn btn-default btn-flat  btn-sm">
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
                        url: 'frm/code/frmCode',
                        dataType: 'json',
                        data: {pid: treeNode['id']}
                    }
                ).then(function (value) {
                    for (var field in value['data']) {
                        $('#code-edit [name="' + field + '"]').val(value['data'][field]);
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
            url: 'frm/code/codeList',
            enable: true,
            type: 'get',
            dataType: 'json',
            autoParam: ["id=parentId"],
            dataFilter: function (treeId, parentNode, childNodes) {
                var node = [];
                $(childNodes['data']).each(function () {
                    node.push({
                        id: this['pid'],
                        name: this['codeName'],
                        open: false,
                        isParent: this['treeType'] === '1',
                        parentId: this['parentId']
                    });
                });
                return node;
            }
        }
    };
    var $zTreeObj = $.fn.zTree.init($("#frm-code-tree"), settings); //初始化树
    var $frmCodeEditForm = $('#code-edit');
    var $codeCommitGroup = $('#code-commit-group');
    $codeCommitGroup.hide();
    $frmCodeEditForm.lock();
    var editUrl;
    var alertText;

    function editFrmCode() {
        var pid = $('#pid').val();
        if (pid && pid.length > 0) {
            $codeCommitGroup.show();
            $frmCodeEditForm.unLock();
            editUrl = 'frm/code/editSysPermission';
            alertText = '修改';
        } else {
            alert('请选择一个节点');
        }
    }

    function saveFrmCode() {
        var pid = $('#pid').val();
        if (pid && pid.length > 0) {
            $codeCommitGroup.show();
            $frmCodeEditForm.reset();
            $frmCodeEditForm.unLock();
            editUrl = 'frm/code/saveSysPermission';
            alertText = '添加';
            $('#parentId').val(pid);
        } else {
            alert('请选择一个节点');
        }
    }

    function submitFrmCode() {
        $.post({
            url: editUrl,
            dataType: 'json',
            async: false,
            data: $frmCodeEditForm.serialize()
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
        $codeCommitGroup.hide();
        $frmCodeEditForm.lock();
        $frmCodeEditForm.reset();
        alertText = undefined;
        editUrl = undefined;
    }

    function deleteFrmCode() {

    }
</script>
</body>
</html>
