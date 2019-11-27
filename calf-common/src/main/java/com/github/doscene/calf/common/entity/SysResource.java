package com.github.doscene.calf.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <h1>权限资源</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysResource<T> extends BaseEntity implements Serializable {
    private String resourceName;
    private String resourceToken;
    private String content;
}
