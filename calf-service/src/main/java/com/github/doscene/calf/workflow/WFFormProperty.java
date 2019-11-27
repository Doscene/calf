package com.github.doscene.calf.workflow;

import lombok.Data;
import org.activiti.engine.form.FormType;

import java.io.Serializable;

/**
 * <h1>com.github.doscene.calf.workflow</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Data
public class WFFormProperty implements Serializable {
    private String id;
    private String name;
    private FormType type;
    private String value;
    private boolean readable;
    private boolean writable;
    private boolean required;

}
