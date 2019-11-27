package com.github.doscene.calf.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <h1>com.github.doscene.calf.common.entity</h1>
 * 字典表
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FrmCode extends BaseEntity implements Serializable {
    private String codeToken;
    private String codeName;
    private String codeText;
    private String parentId;
    private String codeType;
    private String treeType;
}
