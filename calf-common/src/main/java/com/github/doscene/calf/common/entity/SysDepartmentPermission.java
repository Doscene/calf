package com.github.doscene.calf.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <h1>com.github.doscene.calf.common.entity</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDepartmentPermission extends BaseEntity implements Serializable {
    private String departmentId;
    private String permissionId;
}
