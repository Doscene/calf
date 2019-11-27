package com.github.doscene.calf.mapper;

import com.github.doscene.calf.common.entity.SysPermission;
import com.github.doscene.calf.common.entity.SysUserPermission;

import java.util.List;
import java.util.Set;

/**
 * <h1>com.github.doscene.calf.mapper</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface SysUserPermissionMapper {

    int insertOne(SysUserPermissionMapper sysUserPermissionMapper);

    List<SysUserPermission> selectUserPermissionByUserId(String userId);

    List<SysPermission> selectPermissionByUserId(String userId);

    Set<String> selectDepartmentPermissionStringByUserId(String userId);

    Set<String> selectPermissionStringByUserId(String userId);

}
