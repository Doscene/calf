package com.github.doscene.calf.service.security.impl;

import com.github.doscene.calf.common.entity.SysPermission;
import com.github.doscene.calf.common.entity.SysUser;
import com.github.doscene.calf.mapper.SysUserMapper;
import com.github.doscene.calf.mapper.SysUserPermissionMapper;
import com.github.doscene.calf.service.security.RealmService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <h1>com.github.doscene.calf.service.security.impl</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Service
public class RealmServiceImpl implements RealmService {
    private final SysUserPermissionMapper sysUserPermissionMapper;
    private final SysUserMapper sysUserMapper;

    public RealmServiceImpl(SysUserPermissionMapper sysUserPermissionMapper, SysUserMapper sysUserMapper) {
        this.sysUserPermissionMapper = sysUserPermissionMapper;
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public List<SysPermission> getUserPermission(String userId) {
        List<SysPermission> userPermissions = sysUserPermissionMapper.selectPermissionByUserId(userId);
        return userPermissions;
    }


    @Override
    public Set<String> getUserPermissionString(String userId) {
        Set<String> permissions = sysUserPermissionMapper.selectPermissionStringByUserId(userId);
        return permissions;
    }

    @Override
    public Set<String> getUserDepartmentPermissionString(String userId) {
        Set<String> permissions = sysUserPermissionMapper.selectDepartmentPermissionStringByUserId(userId);
        return permissions;
    }

    @Override
    public SysUser getUserByLoginName(String loginName) {
        return sysUserMapper.selectByLoginName(loginName);
    }


}
