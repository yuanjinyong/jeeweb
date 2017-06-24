/**
 * 
 */
package com.jeeweb.framework.core.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * @author 袁进勇
 *
 */
public class RowMap extends TreeMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public RowMap() {
        super();
    }

    public RowMap(final String key, Object value) {
        super();
        put(key, value);
    }

    public RowMap(Map<String, Object> map) {
        super();
        this.putAll(map);
    }

    public String getString(final String key) {
        Object answer = get(key);
        if (answer != null) {
            return answer.toString();
        }
        return null;
    }

    public String getString(final String key, String defaultValue) {
        String answer = getString(key);
        if (answer != null) {
            return answer;
        }
        return defaultValue;
    }

    public Boolean getBoolean(final String key) {
        Object answer = get(key);
        if (answer != null) {
            if (answer instanceof Boolean) {
                return (Boolean) answer;
            } else if (answer instanceof String) {
                return new Boolean((String) answer);
            } else if (answer instanceof Number) {
                return (((Number) answer).intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
            }
        }
        return null;
    }

    public Boolean getBoolean(final String key, Boolean defaultValue) {
        Boolean answer = getBoolean(key);
        if (answer != null) {
            return answer;
        }
        return defaultValue;
    }

    public Number getNumber(final String key) throws ParseException {
        Object answer = get(key);
        if (answer != null) {
            if (answer instanceof Number) {
                return (Number) answer;
            } else if (answer instanceof String) {
                return NumberFormat.getInstance().parse((String) answer);
            }
            throw new ParseException("对象" + answer + "不能解析成为数字！", 0);
        }
        return null;
    }

    public Number getNumber(final String key, Number defaultValue) {
        try {
            Number answer = getNumber(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Byte getByte(final String key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Byte) {
                return (Byte) answer;
            }
            return new Byte(answer.byteValue());
        }
        return null;
    }

    public Byte getByte(final String key, Byte defaultValue) {
        try {
            Byte answer = getByte(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Short getShort(final String key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Short) {
                return (Short) answer;
            }
            return new Short(answer.shortValue());
        }
        return null;
    }

    public Short getShort(final String key, Short defaultValue) {
        try {
            Short answer = getShort(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Integer getInteger(final String key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Integer) {
                return (Integer) answer;
            }
            return new Integer(answer.intValue());
        }
        return null;
    }

    public Integer getInteger(final String key, Integer defaultValue) {
        try {
            Integer answer = getInteger(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Long getLong(final String key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Long) {
                return (Long) answer;
            }
            return new Long(answer.longValue());
        }
        return null;
    }

    public Long getLong(final String key, Long defaultValue) {
        try {
            Long answer = getLong(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Float getFloat(final String key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Float) {
                return (Float) answer;
            }
            return new Float(answer.floatValue());
        }
        return null;
    }

    public Float getFloat(final String key, Float defaultValue) {
        try {
            Float answer = getFloat(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Double getDouble(final String key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Double) {
                return (Double) answer;
            }
            return new Double(answer.doubleValue());
        }
        return null;
    }

    public Double getDouble(final String key, Double defaultValue) {
        try {
            Double answer = getDouble(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public BigDecimal getBigDecimal(final String key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof BigDecimal) {
                return (BigDecimal) answer;
            }
            return new BigDecimal(answer.toString());
        }

        return null;
    }

    public BigDecimal getBigDecimal(final String key, BigDecimal defaultValue) {
        try {
            BigDecimal answer = getBigDecimal(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Map<?, ?> getMap(final String key) {
        Object answer = get(key);
        if (answer != null && answer instanceof Map) {
            return (Map<?, ?>) answer;
        }
        return null;
    }

    public Map<?, ?> getMap(String key, Map<?, ?> defaultValue) {
        Map<?, ?> answer = getMap(key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public void putAll(final ResourceBundle resourceBundle) {
        Enumeration<String> enumeration = resourceBundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            put(key, resourceBundle.getObject(key));
        }
    }

    public void safePut(String key, Object value) {
        if (value != null) {
            put(key, value);
        }
    }

    public void safePut(String key, Object value, Object defaultValue) {
        if (value == null) {
            put(key, defaultValue);
        } else {
            put(key, value);
        }
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public Properties toProperties() {
        Properties answer = new Properties();
        for (Iterator<Map.Entry<String, Object>> iter = entrySet().iterator(); iter.hasNext();) {
            Map.Entry<String, Object> entry = iter.next();
            answer.put(entry.getKey(), entry.getValue());
        }
        return answer;
    }

    public String toXML() {
        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");
        for (Map.Entry<String, Object> entry : this.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            xml.append('<').append(key).append('>');
            if (value instanceof Number) {
                xml.append(value.toString());
            } else {
                xml.append("<![CDATA[").append(value).append("]]>");
            }
            xml.append("</").append(key).append('>');
        }
        xml.append("</xml>");

        return xml.toString();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public RowMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
