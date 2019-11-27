package com.github.doscene.calf.common.entity;


import lombok.Data;

import java.util.Date;

/**
 * <h1>所有实体的父类</h1>
 *
 * @author lds <a href="https://github.com/doscene">github.com/doscene</a>
 */
@Data
public class BaseEntity {
    /**
     * 主键
     */
    private String pid;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最近一次修改时间
     */
    private Date updateTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建IP
     */
    private String createIp;
    /**
     * 是否有效 0无效 1有效
     * <b>一般情况下表示该条数据已被【删除】</b>
     */
    private String valuable;
    /**
     * 过期时间
     */
    private Date expiredTime;
    /**
     * 部门
     */
    private String department;
}
