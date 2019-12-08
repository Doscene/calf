<%--
  User: doscene
  Date: 2019/11/21
  Time: 11:36
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>用户管理-授权</title>
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
                            <button type="button" class="btn btn-default btn-flat  btn-sm" onclick=""><i class="fa fa-save text-green"></i>&nbsp;保存
                            </button>
                        </div>
                        <div class="form-group">
                            <button onclick="window.close()" type="button" class="btn btn-flat btn-default btn-sm"><i
                                    class="fa fa-close text-red"></i>&nbsp;关闭
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="box-body no-padding">
                <div class="col-sm-4">
                    <div><i class="fa fa-university"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;授权列表</div>
                    <ul id="sys-permission-tree" class="ztree"></ul>

                </div>
                <div class="col-sm-4">
                    <div><i class="fa fa-university"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已授权列表</div>
                    <ul id="sys-permission-tree-checked" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="static/plugins/jquery-2.2.4.js"></script>
<script src="static/plugins/calf/common/common.js"></script>
<script src="static/plugins/calf/common/common.jquery.js"></script>
<script src="static/plugins/zTree/js/jquery.ztree.all.js"></script>
<script>
    var firstAsyncSuccessFlag = 0;
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
            },
            onAsyncSuccess: function (event, treeId, msg) {
                var zTree = $zTreeObj;
                if (firstAsyncSuccessFlag == 0) {
                    try {
                        //调用默认展开第一个结点
                        var selectedNode = zTree.getSelectedNodes();
                        var nodes = zTree.getNodes();
                        zTree.expandNode(nodes[0], true);
                        var childNodes = zTree.transformToArray(nodes[0]);
                        zTree.expandNode(childNodes[1], true);
                        zTree.selectNode(childNodes[1]);
                        var childNodes1 = zTree.transformToArray(childNodes[1]);
                        zTree.checkNode(childNodes1[1], true, true);
                        firstAsyncSuccessFlag = 1;
                    } catch (err) {

                    }

                }
            }
        },
        check: {
            enable: true,  //true 、 false 分别表示 显示 、不显示 复选框或单选框*/
            nocheckInherit: true  //当父节点设置 nocheck = true 时，设置子节点是否自动继承 nocheck = true
        },
        async: {
            url: 'sys/user/getUserPermissions',
            enable: true,
            type: 'get',
            dataType: 'json',
            autoParam: ["id=parentId"],
            dataFilter: function (treeId, parentNode, childNodes) {
                var node = [];
                $(childNodes['data']['allPermissions']).each(function () {
                    var checked;
                    var token = this['permissionToken'];
                    console.log(token);
                    for (var i = 0; i < childNodes['data']['userPermissions'].length; i++) {
                        var temp = childNodes['data']['userPermissions'][i];
                        if (temp === token) {
                            //console.log(temp);
                            checked = true;
                        } else {
                            checked = false;
                        }
                    }
                    node.push({
                        id: this['pid'],
                        name: this['permissionName'],
                        isParent: this['treeType'] === '1',
                        parentId: this['parentId'],
                        checked: checked
                    });
                });
                return node;
            }
        }
    };
    var $zTreeObj = $.fn.zTree.init($("#sys-permission-tree"), settings); //初始化树

</script>
</body>
</html>
