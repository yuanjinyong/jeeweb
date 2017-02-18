/**
 * 
 */
package com.jeeweb.framework.core.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author 袁进勇
 *
 */
public class Sort {
    private Map<String, Direction> orderBy = new LinkedHashMap<String, Direction>();
    public static enum Direction {
        ASC, DESC;
        private Direction() {
        }
    }

    public Sort asc(String field) {
        orderBy.put(field, Direction.ASC);
        return this;
    }

    public Sort desc(String field) {
        orderBy.put(field, Direction.DESC);
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Entry<String, Direction> entry : orderBy.entrySet()) {
            sb.append(", ").append(entry.getKey()).append(' ').append(entry.getValue());
        }
        return sb.length() == 0 ? null : sb.substring(2);
    }
}
