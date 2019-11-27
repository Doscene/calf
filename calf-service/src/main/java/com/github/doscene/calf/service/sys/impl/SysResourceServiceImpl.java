package com.github.doscene.calf.service.sys.impl;

import com.github.doscene.calf.common.entity.SysResource;
import com.github.doscene.calf.mapper.SysResourceMapper;
import com.github.doscene.calf.service.sys.SysResourceService;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.sys.impl</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
@Service
public class SysResourceServiceImpl implements SysResourceService {
    @Autowired
    private SysResourceMapper resourceMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SysResource> getResourcePageList(SysResource sysResource, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return resourceMapper.selectResourceBySysResource(sysResource);
    }

    @Override
    @Transactional(readOnly = true)
    public SysResource getResourceByPid(String pid) {
        return resourceMapper.selectByPid(pid);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean saveResource(SysResource sysResource) {
        return resourceMapper.insertOne(sysResource) != 0;
    }

    @Override

    public boolean editResource(SysResource sysResource) {
        return resourceMapper.updateResource(sysResource) != 0;
    }

    @Override
    public boolean deleteResourceByPid(String pid) {
        if (Strings.isNullOrEmpty(pid)) {
            return false;
        }
        return resourceMapper.deleteResource(pid) != 0;
    }
}
