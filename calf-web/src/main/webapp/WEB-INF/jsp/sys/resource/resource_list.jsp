<%--suppress HtmlUnknownTarget --%>
<%--
  User: doscene
  Date: 2019/10/20
  Time: 16:20
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>资源管理-列表</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/AdminLTE.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/skins/skin-blue.css">
    <link rel="stylesheet" href="static/plugins/font-awesome-4.7.0/css/font-awesome.css">
    <script src="static/plugins/calf/common/common.js"></script>
    <style>
        * {
            font-size: 12px;
        }
    </style>
</head>
<body oncontextmenu="return false">
<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <form id="sys-resource-table-search-form" class="form-inline no-padding no-margin" role="form">
                    <div class="pull-left">
                        <div class="form-group">
                            <label for="resource-name">资源名称</label>
                            <input type="text" name="resourceName" class="form-control input-sm" id="resource-name" value="${resource.resourceName}">
                        </div>
                        <div class="form-group">
                            <label for="resource-token">资源标识</label>
                            <input type="text" class="form-control input-sm" name="resourceToken" id="resource-token" value="${resource.resourceToken}">
                        </div>
                    </div>
                    <div class="pull-right">
                        <div class="form-group">
                            <button type="button" class="btn btn-default btn-flat  btn-sm"
                                    onclick="dataTable.ajax.reload()"><i class="fa fa-search text-aqua"></i>&nbsp;查询
                            </button>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-default btn-flat  btn-sm"
                                    onclick="openChildWindow('sys/resource/toSaveResource','用户管理-新增')"><i
                                    class="fa fa-plus text-yellow"></i>&nbsp;新增
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="box-body no-padding">
                <table id="sys-resource-table"
                       class="table table-condensed table-striped table-bordered table-hover"></table>
            </div>
        </div>
    </div>
</div>
<script src="static/plugins/jquery-2.2.4.js"></script>
<script src="static/plugins/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="static/plugins/AdminLTE-2.3.0/plugins/datatables/jquery.dataTables.js"></script>
<script src="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.js"></script>
<script>
    var dataTable = $('#sys-resource-table').DataTable({
        paging: true, //开启分页
        pagingType: "full_numbers",//分页器样式
        autoWidth: false,
        processing: true,
        lengthChange: false,//每页显示多少个选项
        info: true,//页码底端信息
        searching: false,//关闭搜索栏
        serverSide: true,//服务器分页
        ordering: false,//关闭列排序
        ajax: function (data, callback) {
            var pageNum = 1;
            if (data.start > 0) {
                pageNum = (data.start / 10) + 1;
            }
            $.ajax({
                type: "get",
                url: 'sys/resource/resourceList',
                cache: false,
                data: $('#sys-resource-table-search-form').serialize() + '&pageNum=' + pageNum + '&pageSize=' + data.length,
                dataType: "json"
            }).then(function (result) {
                callback({
                    draw: data.draw,
                    data: result['data']['list'],
                    recordsTotal: result['data']['total'],
                    recordsFiltered: result['data']['total']
                });
            }, function (reason) {
                callback({
                    draw: data.draw,
                    data: [],
                    recordsTotal: 0
                })
            });
        },
        columns: [
            {data: 'resourceName', title: '资源名称'},
            {data: 'resourceToken', title: '资源标识'},
            {data: 'createTime', width: '20%', title: '创建时间'},
            {data: 'createBy', title: '创建人'},
            {
                data: null,//自定义返回数据，这里添加一个编辑按钮，并指定其id为后面做编辑功能准备
                className: "text-center",
                render: function (data) {
                    var editBtn = '<button class="btn btn-sm btn-default btn-flat" onclick="openChildWindow(\'sys/resource/toEditResource?pid=' + data['pid'] + '\',\'资源管理-修改\')"><i class="fa fa-edit text-yellow"></i>&nbsp;修改</button>';
                    var deleteBtn = '&nbsp;<button class="btn btn-sm btn-default btn-flat" onclick="deleteResource(\'' + data['resourceName'] + '\',this)"><i class="fa fa-trash text-red"></i>&nbsp;删除</button>';
                    var viewBtn = '&nbsp;<button class="btn btn-sm btn-default btn-flat" onclick="openChildWindow(\'sys/resource/toResourceView?pid=' + data['pid'] + '\',\'资源管理-查看\')"><i class="fa fa-eye text-aqua"></i>&nbsp;查看</button>';
                    return editBtn + deleteBtn + viewBtn;
                }
                , width: '20%',
                title: '操作'
            }
        ],
        language:
            {
                "sProcessing":
                    "处理中...",
                "sLengthMenu":
                    "显示 _MENU_ 项结果",
                "sZeroRecords":
                    "没有匹配结果",
                "sInfo":
                    "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty":
                    "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered":
                    "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix":
                    "",
                "sSearch":
                    "搜索:",
                "sUrl":
                    "",
                "sEmptyTable":
                    "表中数据为空",
                "sLoadingRecords":
                    "载入中...",
                "sInfoThousands":
                    ",",
                "oPaginate":
                    {
                        "sFirst":
                            "首页",
                        "sPrevious":
                            "上页",
                        "sNext":
                            "下页",
                        "sLast":
                            "末页"
                    }
                ,
                "oAria":
                    {
                        "sSortAscending":
                            ": 以升序排列此列",
                        "sSortDescending":
                            ": 以降序排列此列"
                    }
            }
    });

    function deleteResource(loginName, e) {
        if (confirm("确认删除资源【" + loginName + "】？")) {
            $.ajax({
                type: "get",
                url: 'sys/user/deleteResource',
                cache: false,
                data: {loginName: loginName},
                dataType: "json"
            }).then(function (value) {
                if (value.status === 0) {
                    //移除表格中的对应行。
                    dataTable.row($(e).parents('tr')).remove().draw();
                    alert('操作成功,已删除用户【' + loginName + '】!');
                } else {
                    alert('操作失败');
                }
            }, function () {
                alert('网络异常');
            });
        }
    }
</script>
</body>
</html>
