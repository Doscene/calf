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
    <title>用户管理-查看</title>
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
    <form id="user-view-form" autocomplete="off" readonly disabled>
        <div class="box-header with-border">
            <h3 class="box-title">用户管理-查看</h3>
            <div class="pull-right">
                <button type="button" onclick="location.href='sys/user/toUserEdit?loginName=${sysUser.loginName}';" class="btn btn-flat btn-default btn-sm">
                    <i class="fa fa-pencil text-warning"></i>&nbsp;编辑
                </button>
                <button type="button" onclick="window.close()" class="btn btn-flat btn-default btn-sm"><i
                        class="fa fa-close text-red"></i>&nbsp;关闭
                </button>
            </div>
        </div>
        <div class="box-body">
            <table class="no-padding no-margin form-table">
                <tr>
                    <%--<td rowspan="9">头像</td>--%>
                    <td rowspan="9" colspan="2" style="align-items: center;width: 15%">
                        <img src="static/images/nopic.jpg" alt="头像">
                    </td>
                </tr>
                <tr>
                    <td class="header-input">登录名</td>
                    <td class="body-input">${sysUser.loginName}</td>
                    <td class="header-input">姓名</td>
                    <td class="body-input">${sysUser.trueName}</td>
                </tr>
                <tr>
                    <td class="header-input">邮箱</td>
                    <td class="body-input">${sysUser.email}</td>
                    <td class="header-input">头像</td>
                    <td class="body-input">${sysUser.headPortrait}</td>
                </tr>
                <tr>
                    <td class="header-input">年龄</td>
                    <td class="body-input">${sysUser.age}</td>
                    <td class="header-input">性别</td>
                    <td class="body-input">${fns:getSubCode('GENDER_TYPE',sysUser.gender)}</td>
                </tr>
                <tr>
                    <td class="header-input">生日</td>
                    <td class="body-input"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
                                                           value="${sysUser.birthday}"/></td>
                    <td class="header-input">创建者</td>
                    <td class="body-input">${sysUser.createBy}</td>
                </tr>
                <tr>
                    <td class="header-input">创建IP</td>
                    <td class="body-input">${sysUser.createIp}</td>
                    <td class="header-input">所属部门</td>
                    <td class="body-input">${sysUser.department}</td>
                </tr>
                <tr>
                    <td class="header-input">电话号码</td>
                    <td class="body-input">${sysUser.phone}</td>
                    <td class="header-input">过期时间</td>
                    <td class="body-input"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sysUser.expiredTime}"/></td>
                </tr>
                <tr>
                    <td class="header-input">更新时间</td>
                    <td class="body-input"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sysUser.updateTime}"/></td>
                    <td class="header-input">创建时间</td>
                    <td class="body-input"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sysUser.createTime}"/></td>

                </tr>
                <tr>
                    <td class="header-input">最近登录IP</td>
                    <td class="body-input">${sysUser.lastLoginIp}</td>
                    <td class="header-input">最近登录时间</td>
                    <td class="body-input"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sysUser.lastLoginTime}"/></td>
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
