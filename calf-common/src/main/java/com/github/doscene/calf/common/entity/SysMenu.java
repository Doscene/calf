package com.github.doscene.calf.common.entity;

import com.alibaba.fastjson.JSON;
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
public class SysMenu extends BaseEntity implements Serializable {
    private String menuName;
    private String description;
    private String url;
    private String icon;
    private String parentId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
