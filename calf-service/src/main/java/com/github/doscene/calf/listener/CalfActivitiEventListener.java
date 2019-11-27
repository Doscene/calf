package com.github.doscene.calf.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.springframework.stereotype.Component;

/**
 * <h1>com.github.doscene.calf.event</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Component
@Slf4j
public class CalfActivitiEventListener implements ActivitiEventListener {
    @Override
    public void onEvent(ActivitiEvent event) {
        switch (event.getType()) {
            case JOB_EXECUTION_SUCCESS:
                log.debug("工作流执行成功");
                break;
            case JOB_EXECUTION_FAILURE:
                log.debug("工作流执行失败");
                break;
            default:
                System.out.println("Event received: " + event.getType());
        }
    }

    /**
     * 事件监听处理过程错误，也不会触发工作流处理过程异常
     *
     * @return 结果
     */
    @Override
    public boolean isFailOnException() {
        return false;
    }
}
