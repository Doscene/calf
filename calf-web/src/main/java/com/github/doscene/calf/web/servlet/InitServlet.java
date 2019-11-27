package com.github.doscene.calf.web.servlet;

import com.alibaba.fastjson.JSON;
import com.github.doscene.calf.common.utils.MapperHotSwap;
import com.github.doscene.calf.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Set;

/**
 * <h1>com.github.doscene.calf.web.servlet</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
public class InitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        SpringUtils.getBean(MapperHotSwap.class).start();
        RedisTemplate redisTemplate = SpringUtils.getBean(RedisTemplate.class);
        Set s = redisTemplate.keys("*");
        System.out.println(JSON.toJSONString(s));
    }
}
