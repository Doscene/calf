package com.github.doscene.calf.common.controller;

import com.github.doscene.calf.common.dto.RestfulResult;
import com.github.pagehelper.PageInfo;

import java.util.Collection;
import java.util.List;

/**
 * <h1>com.github.doscene.calf.common.controller</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public abstract class BaseController<T> {

    protected RestfulResult exec(T t) {
        return RestfulResult.ok(t);
    }

    protected RestfulResult exec(Collection<T> list) {
        return RestfulResult.ok(list);
    }

    /**
     * 将列表转换为分页数据
     * 1.必须使用pagehelper进行分页
     *
     * @param list list
     * @return data
     */
    protected RestfulResult execPage(List<T> list) {
        return RestfulResult.ok(new PageInfo<>(list));
    }

    protected RestfulResult execBoolean(Boolean tf) {
        if (tf != null && tf) {
            return RestfulResult.ok();
        }
        return RestfulResult.error();
    }
}
