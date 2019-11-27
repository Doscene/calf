package com.github.doscene.calf.security;

import com.alibaba.fastjson.JSON;
import com.github.doscene.calf.common.entity.SysUser;
import org.apache.shiro.SecurityUtils;

/**
 * <h1>安全协议</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public class UserUtils {
    /**
     * 获取当前登录用户
     *
     * @return 用户
     */
    public static SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当权登录用户名
     *
     * @return 用户名
     */
    public static String getUserName() {
        return UserUtils.getUser().getLoginName();
    }
    public static void main(String[] args) {
        String csys="BG";
        System.out.println(JSON.toJSONString(csys.split("")));
        System.out.println(csys.substring(0,csys.length()-1));
    }
}
