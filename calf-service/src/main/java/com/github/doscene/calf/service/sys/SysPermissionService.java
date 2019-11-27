package com.github.doscene.calf.service.sys;

import com.github.doscene.calf.common.entity.SysPermission;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.sys</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface SysPermissionService {
    List<SysPermission> getSysPermissionByParentId(String pid);

    SysPermission getSysPermissionByPid(String pid);

    boolean saveSysPermission(SysPermission sysPermission);

    boolean editSysPermission(SysPermission sysPermission);
}
