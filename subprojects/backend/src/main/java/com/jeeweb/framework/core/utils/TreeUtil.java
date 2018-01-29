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
    public static <T extends TreeNodeEntity<?, T>> List<T> treeToList(List<T> nodeList) {
        List<T> list = new ArrayList<>();
        for (T node:nodeList) {
            list.add(node);
            if (node.getChildren() != null) {
                list.addAll(treeToList(node.getChildren()));
            }
        }

        return list;
    }

    /**
     * 把列表形式的转换为树形的结构
     * 注意：列表形式的必须是已经排好序的，父节点必须排在子节点的前面
     * 
     * @param nodeList
     * @return
     */
    public static <T extends TreeNodeEntity<?, T>> List<T> listToTree(List<T> nodeList) {
        // 找出顶层的节点返回
        List<T> nodeTree = new ArrayList<>();

        // 组织好父子关系
        Map<Object, T> nodeMap = new HashMap<>();
        for (T node : nodeList) {
            nodeMap.put(node.getF_id(), node);

            // T parentNode = nodeMap.get(node.getF_parent_id());
            T parentNode = getParentNode(nodeMap, node.getF_parent_id(), node.getF_parent_path());
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
    
    public static <T extends TreeNodeEntity<?, T>> List<T> toTreeForList(List<T> nodeList) {
    	List<T> nodeTree = new ArrayList<>();
    	 // 组织好父子关系
        Map<Object, T> nodeMap = new HashMap<>();
        for (T node : nodeList) {
            nodeMap.put(node.getF_id(), node);
            // T parentNode = nodeMap.get(node.getF_parent_id());
            T parentNode = getParentNode(nodeMap, node.getF_parent_id(), node.getF_parent_path());
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
    

    public static List<RowMap> listToTree(List<RowMap> nodeList, String f_id) {
        return listToTree(nodeList, f_id, "f_parent_id", "f_parent_path");
    }

    @SuppressWarnings("unchecked")
    public static List<RowMap> listToTree(List<RowMap> nodeList, String f_id, String f_parent_id,
            String f_parent_path) {
        // 找出顶层的节点返回
        List<RowMap> nodeTree = new ArrayList<>();

        // 组织好父子关系
        Map<Object, RowMap> nodeMap = new HashMap<>();
        for (RowMap node : nodeList) {
            nodeMap.put(node.get(f_id), node);

            // RowMap parentNode = nodeMap.get(node.get(f_parent_id));
            RowMap parentNode = getParentNode(nodeMap, node.get(f_parent_id), node.$(f_parent_path));
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

    private static <T> T getParentNode(Map<Object, T> nodeMap, Object f_parent_id, String f_parent_path) {
        if (f_parent_id != null) {
            T parentNode = nodeMap.get(f_parent_id);
            if (parentNode != null) {
                return parentNode;
            }

            if (!HelpUtil.isEmpty(f_parent_path)) {
                String[] f_parent_ids = f_parent_path.split("/");
                if (!HelpUtil.isEmpty(f_parent_ids)) {
                    for (int i = f_parent_ids.length - 1; i >= 0; i--) {
                        Object key = f_parent_ids[i];
                        if (f_parent_id instanceof Integer) {
                            key = Integer.valueOf(f_parent_ids[i]);
                        }
                        parentNode = nodeMap.get(key);
                        if (parentNode != null) {
                            return parentNode;
                        }
                    }
                }
            }
        }

        return null;
    }
}
