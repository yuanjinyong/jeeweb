/**
 * 
 */
package com.jeeweb.framework.core.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.jeeweb.framework.core.model.Result;

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

        PrintWriter out = response.getWriter();
        out.println(JsonUtil.toString(result == null ? new Result() : result));

        out.flush();
        out.close();
    }
}
