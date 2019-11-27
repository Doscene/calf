package com.github.doscene.calf.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <h1>权限</h1>
 *
 * @author lds <a href="https://github.com/doscene">github.com/doscene</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPermission extends BaseEntity implements Serializable {
    private String permissionName;
    private String permissionToken;
    private String resourceId;
    private String operationId;
    private String permissionType;
    private String treeType;
    private String parentId;
    private Integer sort;
    /*************关联实体*****************/
    private SysResource resource;
    private SysOperation operation;
}
