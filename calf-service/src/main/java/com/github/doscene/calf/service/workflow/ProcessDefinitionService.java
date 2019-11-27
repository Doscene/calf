package com.github.doscene.calf.service.workflow;

import com.github.doscene.calf.workflow.WFProcessDefinitionExt;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.workflow</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface ProcessDefinitionService {
    List<WFProcessDefinitionExt> getProcessList(ProcessDefinitionEntity processDefinitionEntity, Integer pageNum, Integer pageSize);
}
