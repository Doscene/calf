package com.github.doscene.calf.mapper;

import com.github.doscene.calf.common.entity.SysResource;

import java.util.List;

/**
 * <h1>权限资源管理</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface SysResourceMapper {
    int insertOne(SysResource sysResource);

    SysResource selectByPid(String pid);

    List<SysResource> selectResourceBySysResource(SysResource sysResource);

    int updateResource(SysResource sysResource);

    int deleteResource(String pid);
}
