package com.github.doscene.calf.service.security;

import com.github.doscene.calf.common.entity.SysPermission;
import com.github.doscene.calf.common.entity.SysUser;

import java.util.List;
import java.util.Set;

/**
 * <h1>com.github.doscene.calf.service.security</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface RealmService {
    /**
     * 根据用户ID获取用户对应的权限列表
     *
     * @return 权限列表
     */
    List<SysPermission> getUserPermission(String userId);


    /**
     * 根据用户ID获取用户对应的权限字符串列表
     *
     * @return 权限列表
     */
    Set<String> getUserPermissionString(String userId);

    /**
     * 根据用户ID获取用户对应部门的权限字符串列表
     *
     * @return 权限列表
     */
    Set<String> getUserDepartmentPermissionString(String userId);

    /**
     * 根据用户名称获取用户
     *
     * @param loginName 登录名
     * @return 用户
     */
    SysUser getUserByLoginName(String loginName);


}
