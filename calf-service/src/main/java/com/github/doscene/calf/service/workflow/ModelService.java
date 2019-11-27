package com.github.doscene.calf.service.workflow;

import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Model;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.service.workflow</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public interface ModelService {

    List<Model> getModelList(ModelEntity modelEntity, Integer pageNum, Integer pageSize);

    Model getModelById(String modelId);
}
