package com.jeeweb.framework.business.web.controller;

import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.Page;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.framework.core.utils.HelpUtil;

public abstract class SuperController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Timestamp.class, new TimestampEditor());
    }

    /**
     * 直接获取参数
     */
    public static String $(String paramName) {
        return getRequest().getParameter(paramName);
    }

    public static String $(String paramName, String defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmpty(value) ? defaultValue : value;
    }

    public static Boolean $bool(String paramName) {
        return $bool(paramName, null);
    }

    public static Boolean $bool(String paramName, Boolean defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmpty(value) ? defaultValue : Boolean.parseBoolean(value);
    }

    public static Integer $int(String paramName) {
        return $int(paramName, null);
    }

    public static Integer $int(String paramName, Integer defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmpty(value) ? defaultValue : Integer.parseInt(value);
    }

    public static Long $long(String paramName) {
        return $long(paramName, null);
    }

    public static Long $long(String paramName, Long defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmpty(value) ? defaultValue : Long.parseLong(value);
    }

    public static Float $float(String paramName) {
        return $float(paramName, null);
    }

    public static Float $float(String paramName, Float defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmpty(value) ? defaultValue : Float.parseFloat(value);
    }

    public static Double $double(String paramName) {
        return $double(paramName, null);
    }

    public static Double $double(String paramName, Double defaultValue) {
        String value = $(paramName);
        return HelpUtil.isEmpty(value) ? defaultValue : Double.parseDouble(value);
    }

    public static ParameterMap $params() {
        return $params(null);
    }

    public static <T> Page<T> $page() {
        Page<T> page = new Page<>($int(ParameterMap.PAGE_SIZE, 0), $int(ParameterMap.PAGE_NO, 0));
        page.setOrderBy($(ParameterMap.ORDER_BY, null));
        return page;
    }

    public static ParameterMap $params(String prefix) {
        ParameterMap params = new ParameterMap();

        int prefixLength = prefix == null ? 0 : prefix.length();
        HttpServletRequest request = getRequest();
        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if (prefixLength == 0 || paramName.startsWith(prefix)) {
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(paramName.substring(prefixLength), values);
                } else {
                    params.put(paramName.substring(prefixLength), values[0]);
                }
            }
        }

        return params;
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public static Object $attr(String attrKey) {
        return getRequest().getAttribute(attrKey);
    }

    public static void $attr(String attrKey, Object attrValue) {
        getRequest().setAttribute(attrKey, attrValue);
    }

    public static void $attrs(Object... args) {
        HttpServletRequest request = getRequest();
        for (int i = 1; i < args.length; i += 2) {
            request.setAttribute(String.valueOf(args[i - 1]), args[i]);
        }
    }

    protected static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    protected static ServletContext getServletContext() {
        return getRequest().getSession().getServletContext();
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /** 基于@ExceptionHandler异常处理 */
    @ExceptionHandler
    @ResponseBody
    public ResponseResult exceptionHandler(HttpServletRequest request, Exception e) {
        String errorMsg = "";
        if (e instanceof DataAccessException) {
            Throwable root = ((DataAccessException) e).getRootCause();
            errorMsg = root != null ? root.getMessage() : ((DataAccessException) e).getMessage();
        } else if (e instanceof BusinessException) {
            errorMsg = ((BusinessException) e).getFormattedMessage();
        } else if (e instanceof NoSuchMethodException) {
            errorMsg = "请求的方法不存在!";
        } else if (e instanceof BindException) {
            errorMsg = "类型转换错误!";
        } else {
            errorMsg = e.getMessage();
            if (HelpUtil.isEmpty(errorMsg)) {
                errorMsg = e.toString();
            }
        }
        log.error(e.getMessage(), e);

        return new ResponseResult(new Result(errorMsg), HttpStatus.OK);
    }
}
