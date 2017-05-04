/**
 * 
 */
package com.jeeweb.platform.security.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;

import com.jeeweb.framework.core.model.Result;
import com.jeeweb.framework.core.utils.JsonUtil;
import com.jeeweb.platform.security.RestTokenService;

/**
 * @author 袁进勇
 *
 */
public final class ResponseUtil {
    private ResponseUtil() {

    }

    public static void success(HttpServletResponse response) throws IOException {
        result(response, HttpServletResponse.SC_OK, null);
    }

    public static void success(HttpServletResponse response, Result result) throws IOException {
        result(response, HttpServletResponse.SC_OK, result);
    }

    public static void failure(HttpServletResponse response, int sc, Result result) throws IOException {
        result(response, sc, result);
    }

    private static void result(HttpServletResponse response, int sc, Result result) throws IOException {
        response.setStatus(sc);
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, CorsConfiguration.ALL);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, CorsConfiguration.ALL);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, RestTokenService.REST_TOKEN);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, StringUtils.collectionToCommaDelimitedString(
                Arrays.asList(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)));

        PrintWriter out = response.getWriter();
        out.println(JsonUtil.toString(result == null ? new Result() : result));

        out.flush();
        out.close();
    }
}
