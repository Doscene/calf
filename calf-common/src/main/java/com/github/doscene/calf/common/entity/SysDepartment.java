package com.github.doscene.calf.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <h1>权限组(部门)</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDepartment extends BaseEntity implements Serializable {
    private String departmentName;
}
