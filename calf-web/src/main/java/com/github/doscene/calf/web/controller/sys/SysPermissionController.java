package com.github.doscene.calf.web.controller.sys;

import com.github.doscene.calf.common.controller.BaseController;
import com.github.doscene.calf.common.dto.RestfulResult;
import com.github.doscene.calf.common.entity.SysPermission;
import com.github.doscene.calf.security.UserUtils;
import com.github.doscene.calf.service.sys.SysPermissionService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <h1>com.github.doscene.calf.web.controller.sys</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Controller
@RequestMapping("sys/permission")
public class SysPermissionController extends BaseController<SysPermission> {

    private final SysPermissionService sysPermissionService;

    public SysPermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    /**
     * 权限列表
     *
     * @param sysPermission 权限实体
     * @return 视图
     */
    @RequestMapping("toPermissionList")
    public String toPermissionList(@ModelAttribute("sysPermission") SysPermission sysPermission) {
        return "sys/permission/permission_list";
    }

    /**
     * 获取权限列表
     *
     * @param sysPermission 权限实体
     * @return 视图
     */
    @ResponseBody
    @RequestMapping("permissionList")
    public RestfulResult PermissionList(SysPermission sysPermission) {
        if (Strings.isNullOrEmpty(sysPermission.getParentId())) {
            sysPermission.setParentId("1");
        }
        return exec(sysPermissionService.getSysPermissionByParentId(sysPermission.getParentId()));
    }

    @ResponseBody
    @RequestMapping("permission")
    public RestfulResult permission(SysPermission sysPermission) {
        return exec(sysPermissionService.getSysPermissionByPid(sysPermission.getPid()));
    }

    @ResponseBody
    @RequestMapping("savePermission")
    public RestfulResult savePermission(SysPermission sysPermission) {
        sysPermission.setCreateBy(UserUtils.getUserName());
        return execBoolean(sysPermissionService.saveSysPermission(sysPermission));
    }

    @ResponseBody
    @RequestMapping("editPermission")
    public RestfulResult editPermission(SysPermission sysPermission) {
        return execBoolean(sysPermissionService.editSysPermission(sysPermission));
    }
}
