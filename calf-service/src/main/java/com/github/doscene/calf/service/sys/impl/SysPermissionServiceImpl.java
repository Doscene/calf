package com.github.doscene.calf.service.sys.impl;

import com.github.doscene.calf.common.entity.SysPermission;
import com.github.doscene.calf.mapper.SysPermissionMapper;
import com.github.doscene.calf.service.sys.SysPermissionService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.sys.impl</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Service
@Slf4j
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> getSysPermissionByParentId(String pid) {
        SysPermission param = new SysPermission();
        param.setParentId(pid);
        return sysPermissionMapper.selectSysPermissionByParentId(param);
    }

    @Override
    public SysPermission getSysPermissionByPid(String pid) {
        if (Strings.isNullOrEmpty(pid)) {
            return null;
        }
        return sysPermissionMapper.selectSysPermissionByPid(pid);
    }

    @Override
    public boolean saveSysPermission(SysPermission sysPermission) {
        return sysPermissionMapper.insertOne(sysPermission) != 0;
    }

    @Override
    public boolean editSysPermission(SysPermission sysPermission) {
        return sysPermissionMapper.updateSysPermission(sysPermission) != 0;
    }

}
