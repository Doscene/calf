package com.github.doscene.calf.service.workflow.impl;

import com.github.doscene.calf.service.workflow.ModelService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Model;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.workflow.impl</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
@Service
public class ModelServiceImpl implements ModelService {

    private final RepositoryService repositoryService;

    public ModelServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public List<Model> getModelList(ModelEntity modelEntity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return repositoryService.createModelQuery().list();
    }

    @Override
    public Model getModelById(String modelId) {
        return repositoryService.createModelQuery().modelId(modelId).singleResult();
    }
}
