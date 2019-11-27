package com.github.doscene.calf.workflow;

import lombok.Data;

import java.util.Map;

/**
 * <h1>com.github.doscene.calf.common.entity</h1>
 * 流程定义扩展
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Data
public class WFProcessDefinitionExt {
    private String id;
    protected String key;
    protected String name;
    protected String description;
    protected Map<String, Object> variables;
    protected String deploymentId;
    protected int revision = 1;
    protected int version;
    protected String category;
}
