package com.github.doscene.calf.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <h1>操作日志log</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Data
public class SysLog  implements Serializable {
    private String pid;
    private Timestamp createTime;
    private Timestamp updateTime;
    //是否被删除
    private String isDelete;
    //注释
    private String note;
    //创建人
    private String createBy;
    //类名
    private String className;
    //操作方法名称
    private String methodName;
    //标签
    private String tags;
    //操作的表
    private String tableName;
    //参数列表
    private String parameters;
    //返回值
    private String result;
    //异常
    private String exceptionNote;
    //异常类型
    private String exceptionClass;
    //IP
    private String ip;
}
