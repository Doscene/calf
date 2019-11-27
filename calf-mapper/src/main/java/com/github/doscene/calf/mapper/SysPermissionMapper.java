package com.github.doscene.calf.mapper;

import com.github.doscene.calf.common.entity.SysPermission;

import java.util.List;

/**
 * <h1>权限映射</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface SysPermissionMapper {
    /**
     * 插入一个权限
     *
     * @param permission 权限实体
     * @return res
     */
    int insertOne(SysPermission permission);

    /**
     * 查询权限列表
     *
     * @param permission 权限
     * @return 权限列表
     */
    List<SysPermission> selectSysPermissionByParentId(SysPermission permission);

    SysPermission selectSysPermissionByPid(String pid);

    int updateSysPermission(SysPermission sysPermission);
}
