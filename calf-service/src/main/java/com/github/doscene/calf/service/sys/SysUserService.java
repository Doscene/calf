package com.github.doscene.calf.service.sys;

import com.github.doscene.calf.common.entity.SysPermission;
import com.github.doscene.calf.common.entity.SysUser;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.sys</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface SysUserService {
    /**
     * 根据用户登录名查询
     *
     * @param loginName 登录名
     * @return 用户
     */
    SysUser getUserByLoginName(String loginName);

    /**
     * 查询用户
     *
     * @param sysUser  用户
     * @param pageNum  分页
     * @param pageSize 页长度
     * @return 列表
     */
    List<SysUser> getUserList(SysUser sysUser, Integer pageNum, Integer pageSize);

    /**
     * 注册用户
     *
     * @param user 用户bean
     * @return 消息状态
     */
    boolean register(SysUser user);

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return 结果
     */
    boolean deleteSysUser(SysUser sysUser);

    /**
     * 编辑用户
     *
     * @param sysUser 用户
     * @return 结果
     */
    boolean editSysUser(SysUser sysUser);

}
