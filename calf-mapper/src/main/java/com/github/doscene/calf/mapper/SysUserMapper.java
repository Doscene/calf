package com.github.doscene.calf.mapper;

import com.github.doscene.calf.common.entity.SysUser;

import java.util.List;

/**
 * <h1>系统用户</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface SysUserMapper {
    /**
     * 创建用户
     *
     * @param sysUser 实体
     * @return 结果
     */
    int insertOne(SysUser sysUser);

    /**
     * 根据登录名查询用户
     *
     * @param loginName 用户名
     * @return 实体
     */
    SysUser selectByLoginName(String loginName);

    /**
     * 根据用户实体查询用户
     *
     * @param sysUser 用户实体
     * @return 结果
     */
    List<SysUser> selectBySysUser(SysUser sysUser);

    /**
     * 删除用户
     * TODO  将删除数据移到删除表
     *
     * @param sysUser 用户
     * @return 结果
     */
    int deleteSysUser(SysUser sysUser);

    /**
     * 编辑用户
     *
     * @param sysUser 用户
     * @return 结果
     */
    int editSysUser(SysUser sysUser);
}
