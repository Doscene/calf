package com.github.doscene.calf.common.annotation;

import com.github.doscene.calf.common.dto.TreeNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h1>树形节点工具类</h1>
 * <ol>
 * <li>本类只能解析带有{@link com.github.doscene.calf.common.annotation.TreeNodeFlag}注解的类</li>
 * <li>被解析的类需要严格按照{@link com.github.doscene.calf.common.annotation.TreeNodeFlag}注解所标注的字段签名对应</li>
 * </ol>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
public class TreeNodeUtils {

    /**
     * 将实体列表<b>递归</b>封装为树形插件
     *
     * @param <T>    泛型
     * @param origin 列表
     * @return 封装好的树形插件
     */
    public static <T> List<TreeNode<T>> convertToTree(List<T> origin, Class<T> clazz, String root)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (origin == null || origin.size() <= 0) {
            return Collections.emptyList();
        }
        // 进行递归
        final TreeNodeFlag treeNodeFlag = clazz.getAnnotation(TreeNodeFlag.class);
        if (treeNodeFlag == null) {
            throw new RuntimeException("未找到树形节点标记注解");
        }
        return recurrence(origin, root, treeNodeFlag);
    }

    /**
     * 递归生成
     *
     * @param <T>          泛型
     * @param origin       源列表
     * @param parentId     父节点主键
     * @param treeNodeFlag 注解类型
     * @return 树形节点
     */
    private static <T> List<TreeNode<T>> recurrence(List<T> origin, String parentId, TreeNodeFlag treeNodeFlag)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<TreeNode<T>> processed = new ArrayList<>();
        for (T t : origin) {
            if (BeanUtils.getProperty(t, treeNodeFlag.parentId()).equals(parentId)) {
                TreeNode<T> children = convectToTreeNode(t, (Class<T>) t.getClass());
                List<TreeNode<T>> c = recurrence(origin, BeanUtils.getProperty(t, treeNodeFlag.id()), treeNodeFlag);
                children.setChildren(c);
                processed.add(children);
            }
        }
        return processed;
    }

    /**
     * 将普通对象转换为树形节点
     *
     * @param t   需要被转换的对象
     * @param <T> 对象类型
     * @return 树节点
     */
    private static <T> TreeNode<T> convectToTreeNode(T t, Class<T> clazz) throws InvocationTargetException, IllegalAccessException {
        final TreeNodeFlag treeNodeFlag = clazz.getAnnotation(TreeNodeFlag.class);
        if (treeNodeFlag == null) {
            throw new RuntimeException(clazz.getTypeName() + "类未找到树形节点标记注解");
        }
        TreeNode<T> children = new TreeNode<>(t);
        if (!treeNodeFlag.allDefault()) {
            try {
                children.setId(BeanUtils.getProperty(t, treeNodeFlag.id()));
            } catch (Exception e) {
                throw new RuntimeException("节点必须包含主键");
            }
            try {
                children.setChecked(Boolean.parseBoolean(BeanUtils.getProperty(t, treeNodeFlag.checked())));
            } catch (Exception e) {
                children.setChecked(false);
            }
            try {
                children.setHref(BeanUtils.getProperty(t, treeNodeFlag.href()));
            } catch (Exception e) {
                children.setHref("");
            }
            try {
                children.setSpread(Boolean.parseBoolean(BeanUtils.getProperty(t, treeNodeFlag.spread())));
            } catch (Exception e) {
                children.setSpread(false);
            }
            try {
                children.setTitle(BeanUtils.getProperty(t, treeNodeFlag.title()));

            } catch (Exception e) {
                children.setTitle("");
            }
            try {
                children.setDisabled(Boolean.parseBoolean(BeanUtils.getProperty(t, treeNodeFlag.disabled())));
            } catch (Exception e) {
                children.setDisabled(false);
            }
        } else {
            BeanUtils.copyProperties(children, t);
        }
        return children;
    }

    /**
     * 在<code>origin</code>列表中查询出<code>pid</code>为<code>parentId</code>的节点。
     *
     * @param origin   源列表
     * @param clazz    类型
     * @param parentId 父节点标识
     * @param <T>      泛型
     * @return 树形节点列表
     */
    public static <T> List<TreeNode<T>> findChildes(List<T> origin, Class<T> clazz, String parentId) {
        List<TreeNode<T>> list = new ArrayList<>();
        if (origin == null || parentId == null) {
            log.warn("资源列表和父节点ID不能为空");
            return list;
        }
        final TreeNodeFlag treeNodeFlag = clazz.getAnnotation(TreeNodeFlag.class);
        if (treeNodeFlag == null) {
            throw new RuntimeException(clazz.getTypeName() + "类未找到树形节点标记注解");
        }
        for (T t : origin) {
            String tmpId;
            try {
                tmpId = BeanUtils.getProperty(t, treeNodeFlag.parentId());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                log.error(clazz.getTypeName() + "类与树形节点注解parentId字段不匹配", e);
                throw new RuntimeException();
            }
            if (parentId.equals(tmpId)) {
                TreeNode<T> node;
                try {
                    node = convectToTreeNode(t, clazz);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                    log.error("转换bean失败", e);
                    throw new RuntimeException();
                }
                list.add(node);
            }
        }
        return list;
    }

}
