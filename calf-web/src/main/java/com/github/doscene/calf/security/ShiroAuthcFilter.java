package com.github.doscene.calf.security;

import com.github.doscene.calf.common.entity.SysUser;
import com.github.doscene.calf.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <h1>自定义shiro过滤器</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
public class ShiroAuthcFilter extends FormAuthenticationFilter {
    private final SysUserMapper sysUserMapper;

    @Autowired
    public ShiroAuthcFilter(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * <h1>登录成功</h1>
     * <ol>
     * <li>更新当前用户登录状态</li>
     * <li>将页面重定向至登录成功页面</li>
     * </ol>
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        try {
            SysUser newU=new SysUser();
            newU.setLastLoginIp(request.getRemoteAddr());
            newU.setLoginName(UserUtils.getUserName());
            newU.setLastLoginTime(new Date());
            sysUserMapper.editSysUser(newU);
        } catch (Throwable t) {
            log.error("登录状态变更失败", t);
        }
        issueSuccessRedirect(request, response);
        return false;
    }

    /**
     * 重定向到登录成功页面
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        if (getSuccessUrl() == null) {
            throw new IllegalStateException("Success URL not available via saved request or via the " +
                    "successUrlFallback method parameter. One of these must be non-null for " +
                    "issueSuccessRedirect() to work.");
        }
        String service = request.getParameter("service");
        if (Strings.isNotBlank(service)) {
            Map<String, String> queryParams = new HashMap<>(3);
            Cookie cookie = new Cookie("token", "token.authenticated");
            cookie.setMaxAge(3600);
            ((HttpServletResponse) response).addCookie(cookie);
            queryParams.put("token", UUID.randomUUID().toString());
            queryParams.put("expire", "3600");
            queryParams.put("date", new Date().toString());
            WebUtils.issueRedirect(request, response, service, queryParams);
        } else {
            WebUtils.issueRedirect(request, response, getSuccessUrl());
        }
    }

    /**
     * <h1>登录失败</h1>
     * <ol>
     * <li>登录错误次数+1</li>
     * <li>更新最后登录时间</li>
     * </ol>
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
     /*   try {

            String loginName = (String) token.getPrincipal();
            if (e instanceof IncorrectCredentialsException) {
                SysUser user = sysUserMapper.selectByLoginName(loginName);
                if (user != null) {
                    user.setLastLoginIp(request.getRemoteAddr());
                    user.setLastLoginTime(new Date());

                    sysUserMapper.updateLoginState(user);
                }
            }
        } catch (Throwable t) {
            log.error("登录状态变更异常", t);
        }*/
        if (log.isDebugEnabled()) {
            log.debug("Authentication exception", e);
        }
        setFailureAttribute(request, e);
        //login failed, let request continue back to the login page:
        return true;
    }

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {

        String errorText = "登录失败";
        if (ae instanceof IncorrectCredentialsException) {
            errorText = "帐号和密码不匹配";
        } else if (ae instanceof UnknownAccountException) {
            errorText = "帐号不存在";
        }
        request.setAttribute(getFailureKeyAttribute(), errorText);
    }


}
