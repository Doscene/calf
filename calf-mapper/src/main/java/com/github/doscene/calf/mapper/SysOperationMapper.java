package com.github.doscene.calf.mapper;

import com.github.doscene.calf.common.entity.SysOperation;

/**
 * <h1></h1>
 *
 * @author lds <a href="https://github.com/doscene">github.com/doscene</a>
 */
public interface SysOperationMapper {
    /**
     * 插入一个权限操作
     *
     * @param operation 操作实体
     * @return res
     */
    int insertOne(SysOperation operation);
}
