<%--suppress HtmlUnknownTarget --%>
<%--
  User: doscene
  Date: 2019/10/11
  Time: 23:13
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="https://github.com.doscene/calf/tld/fns" %>
<html>
<head>
    <title>资源管理-查看</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/AdminLTE.css">
    <link rel="stylesheet" href="static/plugins/AdminLTE-2.3.0/css/skins/skin-blue.css">
    <link rel="stylesheet" href="static/plugins/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="static/plugins/calf/common/common.css">
</head>
<body>
<div class="box">
    <form id="resource-view-form" autocomplete="off" readonly disabled>
        <div class="box-header with-border">
            <h3 class="box-title">资源管理-查看</h3>
            <div class="pull-right">
                <button type="button" onclick="window.close()" class="btn btn-flat btn-default btn-sm"><i
                        class="fa fa-close text-red"></i>&nbsp;关闭
                </button>
            </div>
        </div>
        <div class="box-body">
            <table class="no-padding no-margin form-table">
                <tr>
                    <td class="header-input">资源名称</td>
                    <td class="body-input">${resource.resourceName}</td>
                    <td class="header-input">创建人</td>
                    <td class="body-input">${resource.createBy}</td>
                    <td class="header-input">创建者IP</td>
                    <td class="body-input">${resource.createIp}</td>
                </tr>
                <tr>
                    <td class="header-input">资源标识</td>
                    <td class="body-input">${resource.resourceToken}</td>
                    <td class="header-input">资源内容</td>
                    <td class="body-input">${resource.content}</td>
                    <td class="header-input">管理部门</td>
                    <td class="body-input">${resource.department}</td>
                </tr>
                <tr>
                    <td class="header-input">更新时间</td>
                    <td class="body-input"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${resource.updateTime}" /></td>
                    <td class="header-input">创建时间</td>
                    <td class="body-input"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${resource.createTime}" /></td>
                    <td class="header-input">过期时间</td>
                    <td class="body-input"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${resource.expiredTime}" /></td>
                </tr>

            </table>
        </div>
    </form>
</div>
<script src="static/plugins/jquery-2.2.4.js"></script>
<script src="static/plugins/calf/common/common.js"></script>
<script src="static/plugins/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="static/plugins/AdminLTE-2.3.0/plugins/datatables/jquery.dataTables.js"></script>
<script src="static/plugins/AdminLTE-2.3.0/plugins/datatables/dataTables.bootstrap.js"></script>
</body>
</html>
