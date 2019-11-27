package com.github.doscene.calf.web.servlet;

import com.github.doscene.calf.common.entity.FrmCode;
import com.github.doscene.calf.service.frm.FrmCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>com.github.doscene.calf.web.servlet</h1>
 * 初始化监听器
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
