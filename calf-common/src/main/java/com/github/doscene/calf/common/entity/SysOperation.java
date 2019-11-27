package com.github.doscene.calf.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <h1>权限-操作</h1>
 * 例如：
 * 查看，修改，删除，增加
 *
 * @author lds <a href="https://github.com/doscene">github.com/doscene</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperation extends BaseEntity implements Serializable {
    private String operationName;
    private String operationToken;
}
