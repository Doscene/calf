package com.github.doscene.calf.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.springframework.stereotype.Component;

/**
 * <h1>com.github.doscene.calf.event</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Component
@Slf4j
public class CalfEventListener extends BaseEntityEventListener {
    @Override
    protected void onInitialized(ActivitiEvent event) {
        log.debug("==========》工作流实体初始化【{}】", event.getType());
        super.onInitialized(event);
    }

    @Override
    protected void onCreate(ActivitiEvent event) {
        log.debug("==========》工作流实体创建【{}】", event.getType());
        super.onCreate(event);
    }

    @Override
    protected void onDelete(ActivitiEvent event) {
        log.debug("==========》工作流实体删除【{}】", event.getType());
        super.onDelete(event);
    }

    @Override
    protected void onUpdate(ActivitiEvent event) {
        log.debug("==========》工作流实体更新【{}】", event.getType());
        super.onUpdate(event);
    }

    @Override
    protected void onEntityEvent(ActivitiEvent event) {
        super.onEntityEvent(event);
    }

}
