/**
 * 
 */
package com.jeeweb.framework.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeeweb.framework.business.entity.TreeNodeEntity;
import com.jeeweb.framework.core.model.RowMap;


/**
 * @author 袁进勇
 *
 */
public final class TreeUtil {
    /**
     * 把列表形式的转换为树形的结构
     * 注意：列表形式的必须是已经排好序的，父节点必须排在子节点的前面
     * 
     * @param nodeList
     * @return
     */
    public static <T extends TreeNodeEntity<?, T>> List<T> listToTree(List<T> nodeList) {
        // 找出顶层的节点返回
        List<T> nodeTree = new ArrayList<T>();

        // 组织好父子关系
        Map<Object, T> nodeMap = new HashMap<Object, T>();
        for (T node : nodeList) {
            nodeMap.put(node.getF_id(), node);

            T parentNode = nodeMap.get(node.getF_parent_id());
            if (parentNode == null) {
                nodeTree.add(node);
            } else {
                parentNode.getChildren().add(node);
            }

            if (node.getChildren() == null) {
                node.setChildren(new ArrayList<T>());
            }
        }

        return nodeTree;
    }

    @SuppressWarnings("unchecked")
    public static List<RowMap> listToTree(List<RowMap> nodeList, String f_id, String f_parent_id) {
        // 找出顶层的节点返回
        List<RowMap> nodeTree = new ArrayList<RowMap>();

        // 组织好父子关系
        Map<Object, RowMap> nodeMap = new HashMap<Object, RowMap>();
        for (RowMap node : nodeList) {
            nodeMap.put(node.get(f_id), node);

            RowMap parentNode = nodeMap.get(node.get(f_parent_id));
            if (parentNode == null) {
                nodeTree.add(node);
            } else {
                ((List<RowMap>) parentNode.get("children")).add(node);
            }

            if (!node.containsKey("children")) {
                node.put("children", new ArrayList<RowMap>());
            }
        }

        return nodeTree;
    }
}
