package com.github.doscene.calf.service.security.impl;

import com.github.doscene.calf.common.dto.RestfulResult;
import com.github.doscene.calf.common.entity.SysUser;
import com.github.doscene.calf.mapper.SysUserMapper;
import com.github.doscene.calf.service.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * <h1>用户相关服务</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final SysUserMapper userMapper;

    @Autowired
    public UserServiceImpl(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public RestfulResult register(SysUser user) {
        if (null != userMapper.selectByLoginName(user.getLoginName())) {
            return RestfulResult.build(400, "登录帐号已存在");
        }
        //user.setLoginPassword("123456");
        //生成账号密码加密的盐值
        user.setSalt(UUID.randomUUID().toString());
        //加密后的密码存入数据库
        String cryptoPwd = new SimpleHash("MD5", "123456", user.getSalt(), 3).toString();
        user.setLoginPassword(cryptoPwd);
        if (userMapper.insertOne(user) == 1) {
            return RestfulResult.ok();
        }
        return RestfulResult.build(400, "注册失败");
    }
}
