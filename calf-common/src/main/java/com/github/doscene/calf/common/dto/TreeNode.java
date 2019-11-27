package com.github.doscene.calf.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <h1>树形数据</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Data
public class TreeNode<T> implements Serializable {

    private String id;//节点唯一索引，用于对指定节点进行各类操作	String/Number	任意唯一的字符或数字
    private String title;//	String	标题
    private List<TreeNode<T>> children;//	子节点。支持设定选项同父节点	Array	[{title: '子节点1', id: '111'}]
    private String href;//点击节点弹出新窗口对应的 url。需开启 isJump 参数	String	任意 URL
    private boolean spread = false;    //节点是否初始展开，默认 false	Boolean	true
    private boolean checked = false;//节点是否初始为选中状态（如果开启复选框的话），默认 false	Boolean	true
    private boolean disabled = false;//	节点是否为禁用状态。默认 false	Boolean	false
    private T data;    //数据

    public TreeNode(T data) {
        this.data = data;
    }
}
