package com.github.doscene.calf.service.security.impl;

import com.github.doscene.calf.common.entity.SysPermission;
import com.github.doscene.calf.mapper.SysOperationMapper;
import com.github.doscene.calf.mapper.SysPermissionMapper;
import com.github.doscene.calf.mapper.SysResourceMapper;
import com.github.doscene.calf.service.security.PermissionService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h1>权限服务</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Service
@CacheConfig(cacheNames = "permissions")
public class PermissionServiceImpl implements PermissionService {
    private final SysPermissionMapper sysPermissionMapper;
    private final SysResourceMapper sysResourceMapper;
    private final SysOperationMapper operationMapper;

    public PermissionServiceImpl(SysPermissionMapper permissionMapper, SysResourceMapper sysResourceMapper, SysOperationMapper operationMapper) {
        this.sysPermissionMapper = permissionMapper;
        this.sysResourceMapper = sysResourceMapper;
        this.operationMapper = operationMapper;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean savePermission(SysPermission permission) {
        return false;
    }

    @Override
    public List<SysPermission> getPermissionByParentId(SysPermission permission) {
        return sysPermissionMapper.selectSysPermissionByParentId(permission);
    }
}
