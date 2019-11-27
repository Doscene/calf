package com.github.doscene.calf.security;

import com.github.doscene.calf.common.entity.SysUser;
import com.github.doscene.calf.service.security.RealmService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Set;

/**
 * <h1>shiroRealm</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public class SecurityRealm extends AuthorizingRealm {
    private final RealmService realmService;

    public SecurityRealm(RealmService realmService) {
        this.realmService = realmService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取用户-权限
        Set<String> userPermissions = realmService.getUserPermissionString(UserUtils.getUser().getPid());
        info.addStringPermissions(userPermissions);
        //获取用户-部门-权限
        Set<String> departmentPermissions = realmService.getUserDepartmentPermissionString(UserUtils.getUser().getPid());
        info.addStringPermissions(departmentPermissions);


        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        SysUser sysUser = realmService.getUserByLoginName(username);
        //return null == sysUser ? null : new SimpleAuthenticationInfo(sysUser, sysUser.getLoginPassword(), getName());
        return null == sysUser ? null : new SimpleAuthenticationInfo(sysUser, sysUser.getLoginPassword(), ByteSource.Util.bytes(sysUser.getSalt()), getName());
    }
}

