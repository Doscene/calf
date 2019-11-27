package com.github.doscene.calf.web.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * <h1>druid视图</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@WebServlet(urlPatterns = "/druid/*", name = "druidServlet",
        initParams = {
                @WebInitParam(name = "allow", value = "127.0.0.1"),//IP白名单
                @WebInitParam(name = "deny", value = "192.168.8.201"),//IP黑名单
                @WebInitParam(name = "loginUsername", value = "admin"),//用户名
                @WebInitParam(name = "loginPassword", value = "123456"),//密码
                @WebInitParam(name = "resetEnable", value = "false")//禁用HTML页面上的“Reset All” 功能
        }
)
public class DruidServlet extends StatViewServlet {

}
