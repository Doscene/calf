package com.github.doscene.calf.service.security;

import com.github.doscene.calf.common.entity.SysPermission;

import java.util.List;

/**
 * <h1>权限服务</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface PermissionService {
    /**
     * 保存权限
     *
     * @param permission 权限实体
     * @return 响应
     */
    boolean savePermission(SysPermission permission);

    List<SysPermission> getPermissionByParentId(SysPermission permission);

}
