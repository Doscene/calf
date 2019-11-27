package com.github.doscene.calf.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <h1>系统响应实体</h1>
 * <p>
 * 响应状态码表
 * 状态值      状态说明
 * 0	成功
 * -1          失败
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Data
public class RestfulResult implements Serializable {
    /**
     * 状态码
     */
    private Integer status;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;


    public static RestfulResult ok() {
        return ok(null);
    }

    public static RestfulResult ok(Object data) {
        return build(0, "success", data);
    }

    public static RestfulResult error() {
        return build(400, "failure");
    }

    public static RestfulResult error(String msg) {
        return build(400, msg);
    }

    public static RestfulResult build(Integer status, String msg) {
        if (status == null) {
            throw new IllegalArgumentException("参数【status】不能为空");
        }
        return build(status, msg, null);
    }

    public static RestfulResult build(Integer status, String msg, Object data) {
        return new RestfulResult(status, msg, data);
    }

    private RestfulResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
