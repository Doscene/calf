package com.github.doscene.calf.common.annotation;

import java.lang.annotation.*;

/**
 * <h1>用于树形节点工具类进行反射递归</h1>
 * <ol>
 *     <li></li>
 * </ol>
 * @see com.github.doscene.calf.common.dto.TreeNode
 *
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeNodeFlag {
    /**
     * 全部使用默认字段签名
     * @return	boolean
     */
    boolean allDefault() default false;

    /**
     * 树形节点的主键对应字段
     *
     * @return 字段
     */
    String id() default "pid";

    /**
     * 树形节点的标题对应字段
     *
     * @return 字段
     */
    String title() default "title";

    /**
     * 树形节点的标题对应字段
     *
     * @return 字段
     */
    String parentId() default "parentId";

    /**
     * 是否选中
     *
     * @return 字段
     */
    String checked() default "checked";

    /**
     * 链接
     *
     * @return 字段
     */
    String href() default "href";

    /**
     * 是展开
     *
     * @return 字段
     */
    String spread() default "spread";

    /**
     * 是否禁用
     *
     * @return 字段
     */
    String disabled() default "disabled";
}

