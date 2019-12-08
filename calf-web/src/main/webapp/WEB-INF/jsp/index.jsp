<%--
  User: doscene
  Date: 2019/10/10
  Time: 17:37
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--suppress HtmlUnknownTarget -->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CALF</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
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
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <header class="main-header">
        <div class="logo">
            <span class="logo-mini"><b>Calf</b></span>
            <span class="logo-lg"><b>Calf</b></span>
        </div>
        <nav class="navbar navbar-static-top" role="navigation">
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <div class="collapse navbar-collapse pull-left" id="navbar-collapse">
                <ul class="nav navbar-nav" id="top-user-menu">
                    <c:forEach items="${menu}" var="item" varStatus="status">
                        <li>
                            <a href="javascript:void (0);"
                               onclick="topMenuClick('${item.pid}','${item.permissionName}',this)">${item.permissionName}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li class="dropdown tasks-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-flag-o"></i>
                            <span class="label label-danger">${taskCount}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">你有${taskCount}个待办任务</li>
                            <li>
                                <ul class="menu">
                                    <c:forEach items="${tasks}" var="item">
                                        <li>
                                            <a href="javascript:void(0)">
                                                <h3>
                                                        ${item.category}${item.name}
                                                    <small class="pull-right">20%</small>
                                                </h3>
                                                <div class="progress xs">
                                                    <div class="progress-bar progress-bar-aqua" style="width: 20%"
                                                         role="progressbar"
                                                         aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">20% Complete</span>
                                                    </div>
                                                </div>
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                            <li class="footer">
                                <a href="javascript:void(0)">查看全部待办任务</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="login/logOut"><i class="fa fa-sign-out"></i>&nbsp;注销</a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <aside class="main-sidebar">
        <section class="sidebar">
            <form action="#" method="get" class="sidebar-form">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="搜索...">
                    <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i
                        class="fa fa-search"></i></button>
              </span>
                </div>
            </form>
            <%-- 侧边菜单 --%>
            <ul class="sidebar-menu" id="user-side-menu"></ul>
        </section>
    </aside>
    <div class="content-wrapper no-padding">
        <section class="content-header">
            <div>
                <ol class="breadcrumb" style=" margin: 0 0 5px;" id="bread-navigation-bar">
                </ol>
            </div>
        </section>
        <section class="content no-padding">
            <div class="container-fluid">
                <%-- 主界面 --%>
                <iframe id="content-frame" class="no-padding no-border no-margin" style="width: 100%;min-height: 605px"></iframe>
            </div>
        </section>
    </div>
</div>
<script src="static/plugins/jquery-2.2.4.js"></script>
<script src="static/plugins/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="static/plugins/AdminLTE-2.3.0/js/app.js"></script>
<script src="static/plugins/AdminLTE-2.3.0/plugins/datatables/jquery.dataTables.js"></script>
<script src="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.js"></script>
<script>
    //顶部菜单
    var $topUserMenu = $('#top-user-menu');
    //侧边菜单
    var $userSideMenu = $('#user-side-menu');
    //内容区块iframe
    var $contentFrame = $('#content-frame');
    //面包屑导航栏
    var $breadNavigationBar = $('#bread-navigation-bar');

    //点击顶部菜单拉取对应子级菜单
    function topMenuClick(pid, pName, e) {
        //移除全部菜单激活状态
        $topUserMenu.find('.active').removeClass('active');
        //激活当前菜单
        $(e).parent().addClass('active');
        $breadNavigationBar.empty();
        $breadNavigationBar.append('<li>' + pName + '</li>');
        //拉取侧边菜单
        $.get({
            url: 'index/getPermission',
            data: {parentId: pid},
            dataType: 'json'
        }).then(function (res) {
            if (res.status === 0) {
                $userSideMenu.empty();
                $(res.data).each(function () {
                    var sideMenu = '<li class="tree-view">';
                    var _icon = 'fa fa-link';
                    var _url = '';
                    //树枝节点
                    if (this['treeType'] === '1') {
                        _icon = 'fa fa-folder';
                    }
                    if (this['resource'] && this['resource'] ['content']) {
                        _url = this['resource'] ['content'];
                    }
                    sideMenu += '<a href="javascript:void(0)" onclick="sideMenuClick(\'' + this['pid'] + '\',\'' + this['permissionName'] + '\',\'' + _url + '\',\'' + this['treeType'] + '\',this,2)"><i class="' + _icon + '"></i>' + this['permissionName'] + '</a>';
                    sideMenu += '</li>';
                    $userSideMenu.append(sideMenu);
                });
              var ft=  $userSideMenu.find('li a:first');
              console.log(ft[0]);
              if(ft[0]){
                  ft.trigger('click');
              }else{
                  $contentFrame.attr("src",'');
              }
            }
        }, function (reason) {

        });
    }

    /**
     * 点击侧边菜单触发的事件
     * @param pid   被点击的菜单
     * @param pName 被点击菜单名称
     * @param url   被点击菜单连接
     * @param treeType  被点击菜单是否为叶子节点
     * @param e 被点击菜单dom
     * @param lv    菜单级别
     */
    function sideMenuClick(pid, pName, url, treeType, e, lv) {
        var $currentA = $(e);
        var $currentLi = $currentA.parent();

        $breadNavigationBar.find('li:eq(2)').remove();
        if (lv === 3) {
            $breadNavigationBar.append('<li>' + pName + '</li>');
        } else {
            $breadNavigationBar.find('li:eq(1)').remove();
            $breadNavigationBar.append('<li>' + pName + '</li>');
        }
        if (treeType === '1') {
            $currentLi.find('ul').remove();
            $.get({
                url: 'index/getPermission',
                data: {parentId: pid},
                dataType: 'json',
                async: false
            }).then(function (res) {
                var temp = '<ul class="treeview-menu menu-open">';
                $(res.data).each(function () {
                    var _url = '';
                    if (this['resource'] && this['resource'] ['content']) {
                        _url = this['resource'] ['content'];
                    }
                    temp += '<li style="padding-left: 1em">';
                    temp += '<a href="javascript:void(0)" onclick="sideMenuClick(' +
                        '\'' + this['pid'] + '\',' +
                        '\'' + this['permissionName'] + '\',' +
                        '\'' + _url + '\',' +
                        '\'' + this['treeType'] + '\',' +
                        'this,3)">' +
                        '<i class="fa fa-link"></i>' + this['permissionName'] + '</a>';
                    temp += '</li>';
                });
                temp += '</ul>';
                $currentLi.append(temp);
                $currentLi.find("li:first a").trigger('click');
            }, function (reason) {

            });
        } else {
            if (url && '' !== url) {
                //openChildWindow(url,"阿索");
                $contentFrame.attr("src",url);
            } else {
                $contentFrame.attr("src",'');
            }
        }
    }
    $(function () {
        //页面加载后触发第一个菜单点击事件
        $topUserMenu.find('li>a:first').trigger('click');
    }).ready();
</script>
</body>
</html>