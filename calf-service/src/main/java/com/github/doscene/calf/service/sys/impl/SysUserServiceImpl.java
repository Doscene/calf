package com.github.doscene.calf.service.sys.impl;

import com.github.doscene.calf.common.entity.SysUser;
import com.github.doscene.calf.common.utils.FtpUtils;
import com.github.doscene.calf.mapper.SysUserMapper;
import com.github.doscene.calf.service.sys.SysUserService;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.net.ftp.FtpClient;

import java.util.List;
import java.util.UUID;

/**
 * <h1>com.github.doscene.calf.service.sys.impl</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserMapper userMapper;

    public SysUserServiceImpl(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public SysUser getUserByLoginName(String loginName) {
        return userMapper.selectByLoginName(loginName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysUser> getUserList(SysUser sysUser, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectBySysUser(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean register(SysUser user) {
        if (null != userMapper.selectByLoginName(user.getLoginName())) {
            return false;
        }

        //生成账号密码加密的盐值
        user.setSalt(UUID.randomUUID().toString());
        //加密后的密码存入数据库
        String cryptoPwd = new SimpleHash("MD5", "123456", user.getSalt(), 3).toString();
        user.setLoginPassword(cryptoPwd);
        return userMapper.insertOne(user) == 1;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean deleteSysUser(SysUser sysUser) {
        if (null == userMapper.selectByLoginName(sysUser.getLoginName())) {
            return false;
        }
        return userMapper.deleteSysUser(sysUser) == 1;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean editSysUser(SysUser sysUser) {
        if (null == userMapper.selectByLoginName(sysUser.getLoginName())) {
            return false;
        }
        return userMapper.editSysUser(sysUser) == 1;
    }

}
