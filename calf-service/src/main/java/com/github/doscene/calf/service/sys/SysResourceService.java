package com.github.doscene.calf.service.sys;

import com.github.doscene.calf.common.entity.SysResource;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.sys</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface SysResourceService {
    /**
     * 查询资源进行分页
     *
     * @param sysResource 用户
     * @param pageNum     分页
     * @param pageSize    页长度
     * @return 列表
     */
    List<SysResource> getResourcePageList(SysResource sysResource, Integer pageNum, Integer pageSize);

    SysResource getResourceByPid(String pid);

    boolean saveResource(SysResource sysResource);

    boolean editResource(SysResource sysResource);

    boolean deleteResourceByPid(String pid );
}
