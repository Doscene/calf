package com.github.doscene.calf.service.workflow.impl;

import com.github.doscene.calf.service.workflow.ProcessDefinitionService;
import com.github.doscene.calf.workflow.WFProcessDefinitionExt;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.workflow.impl</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Service
@Slf4j
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

    private final RepositoryService repositoryService;

    public ProcessDefinitionServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public List<WFProcessDefinitionExt> getProcessList(ProcessDefinitionEntity processDefinitionEntity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        ArrayList<WFProcessDefinitionExt> result = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitions) {
            WFProcessDefinitionExt temp = new WFProcessDefinitionExt();
            try {
                BeanUtils.copyProperties(temp, processDefinition);
                result.add(temp);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                log.error("转换工作流定义失败", e);
            }
        }
        return result;
    }
}
