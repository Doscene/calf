package com.github.doscene.calf.service.security;

import com.github.doscene.calf.common.dto.RestfulResult;
import com.github.doscene.calf.common.entity.SysUser;

/**
 * <h1></h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface UserService {
    /**
     * 注册用户
     *
     * @param user 用户bean
     * @return 消息状态
     */
    RestfulResult register(SysUser user);
}
