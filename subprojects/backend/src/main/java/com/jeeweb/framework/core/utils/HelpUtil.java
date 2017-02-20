package com.jeeweb.framework.core.utils;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.jeeweb.framework.core.exception.BusinessException;

public class HelpUtil extends StringUtils {
    private static final String DATETIME_PATTERN_DEFUALT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATETIME_PATTERN_DAYBEGIN = "yyyy-MM-dd 00:00:00";
    private static final String DATETIME_PATTERN_DAYEND = "yyyy-MM-dd 23:59:59";
    private static final String DATETIME_PATTERN_MONTHBEGIN = "yyyy-MM-01 00:00:00";
    private static Map<String, Format> formatCache = new HashMap<>();

    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static String removeEmptyString(String listStr) {
        return removeEmptyString(listStr, ",");
    }

    public static String removeEmptyString(String listStr, String separator) {
        if (isEmpty(listStr)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        String[] sa = listStr.trim().split(separator);
        if (sa.length == 1) {
            return sa[0];
        }

        for (String s : sa) {
            if (!isEmpty(s)) {
                sb.append(',').append(s.trim());
            }
        }
        return sb.substring(1);
    }

    public static String joinToInString(List<?> list) {
        StringBuffer sb = new StringBuffer();
        for (Object e : list) {
            if (e instanceof String) {
                sb.append(",'").append(e).append('\'');
            } else {
                sb.append(',').append(e);
            }
        }
        return sb.substring(1);
    }

    public static List<String> splitToList(String listStr) {
        return splitToList(listStr, ",");
    }

    public static List<String> splitToList(String listStr, String separator) {
        return splitToList(listStr, separator, String.class);
    }

    public static <T> List<T> splitToList(String listStr, Class<T> clz) {
        return splitToList(listStr, ",", clz);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> splitToList(String listStr, String separator, Class<T> clz) {
        if (isEmpty(listStr)) {
            return new ArrayList<>(); // 这里为了方便外面接收者可以继续往list中增加元素，不能用Collections.emptyList()代替。
        }

        String[] sa = listStr.trim().split(separator);
        if (clz == Integer.class) {
            List<Integer> list = new ArrayList<>();
            for (String s : sa) {
                if (!isEmpty(s)) {
                    list.add(Integer.valueOf(s.trim()));
                }
            }
            return (List<T>) list;
        } else {
            List<String> list = new ArrayList<>();
            for (String s : sa) {
                if (!isEmpty(s)) {
                    list.add(s.trim());
                }
            }
            return (List<T>) list;
        }
    }

    /**
     * 获取应用服务器当前时间
     * 
     * @return
     */
    public static Timestamp getNowTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取应用服务器当前时间
     * 
     * @return
     */
    public static String getNowTime(String partten) {
        return getSimpleDateFormat(partten).format(Calendar.getInstance().getTime());
    }

    /**
     * 获取今天的开始时间
     * 
     * @return
     */
    public static String getTodayBeginTime() {
        return getSimpleDateFormat(DATETIME_PATTERN_DAYBEGIN).format(Calendar.getInstance().getTime());
    }

    /**
     * 获取今天的结束时间
     * 
     * @return
     */
    public static String getTodayEndTime() {
        return getSimpleDateFormat(DATETIME_PATTERN_DAYEND).format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当月的开始时间
     * 
     * @return
     */
    public static String getMonthBeginTime() {
        return getSimpleDateFormat(DATETIME_PATTERN_MONTHBEGIN).format(Calendar.getInstance().getTime());
    }

    public static String formatDatetime(Date date) {
        return formatDatetime(date, DATETIME_PATTERN_DEFUALT);
    }

    public static String formatDatetime(Date date, String pattern) {
        return formatDatetime(date.getTime(), pattern);
    }

    public static String formatDatetime(long date) {
        return formatDatetime(date, DATETIME_PATTERN_DEFUALT);
    }

    public static String formatDatetime(long date, String pattern) {
        return getSimpleDateFormat(pattern).format(date);
    }

    public static Date parseDatetime(String dateStr) {
        return parseDatetime(dateStr, DATETIME_PATTERN_DEFUALT);
    }

    public static Date parseDatetime(String dateStr, String pattern) {
        try {
            return getSimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            throw new BusinessException("解析日期失败！", e);
        }
    }

    public static Timestamp parseTimestamp(String dateStr) {
        return parseTimestamp(dateStr, DATETIME_PATTERN_DEFUALT);
    }

    public static Timestamp parseTimestamp(String dateStr, String pattern) {
        return new Timestamp(parseDatetime(dateStr, pattern).getTime());
    }

    private static SimpleDateFormat getSimpleDateFormat(String partten) {
        SimpleDateFormat format = (SimpleDateFormat) formatCache.get(partten);
        if (format == null) {
            format = new SimpleDateFormat(partten);
            formatCache.put(partten, format);
        }

        return format;
    }

    public static Throwable getRootCause(Throwable e) {
        Throwable rootCause = null;
        Throwable cause = e.getCause();
        while (cause != null && cause != rootCause) {
            rootCause = cause;
            cause = cause.getCause();
        }

        return rootCause == null ? e : rootCause;
    }
}