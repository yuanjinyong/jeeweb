/**
 * 
 */
package com.jeeweb.platform.security.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.jeeweb.framework.business.enums.Enums;
import com.jeeweb.platform.sys.entity.UrlEntity;

/**
 * @author 袁进勇
 *
 */
public final class RequestUtil {
    private RequestUtil() {

    }

    public static boolean isMatcher(HttpServletRequest request, List<RequestMatcher> matchers) {
        for (RequestMatcher matcher : matchers) {
            if (matcher.matches(request)) {
                return true;
            }
        }

        return false;
    }

    public static UrlEntity getUrlEntity(HttpServletRequest request,
            Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
        List<RequestMappingInfo> matches = new ArrayList<>();
        for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo info = entry.getKey().getMatchingCondition(request);
            if (info != null) {
                matches.add(info);
            }
        }

        if (matches.size() == 0) {
            return null;
        } else if (matches.size() > 1) {
            Collections.sort(matches, new Comparator<RequestMappingInfo>() {
                @Override
                public int compare(RequestMappingInfo info1, RequestMappingInfo info2) {
                    return info1.compareTo(info2, request);
                }
            });
        }

        return buildUrlEntity(matches.get(0), null);
    }

    public static UrlEntity buildUrlEntity(RequestMappingInfo mapping, HandlerMethod method) {
        UrlEntity entity = new UrlEntity();
        entity.setF_url(mapping.getPatternsCondition().getPatterns().iterator().next().substring(1));
        entity.setF_desc("");
        entity.setF_patterns(mapping.getPatternsCondition().toString());
        entity.setF_methods(mapping.getMethodsCondition().toString());
        entity.setF_params(mapping.getParamsCondition().toString());
        entity.setF_headers(mapping.getHeadersCondition().toString());
        entity.setF_consumes(mapping.getConsumesCondition().toString());
        entity.setF_produces(mapping.getProducesCondition().toString());
        entity.setF_custom(mapping.getCustomCondition() == null ? "[]" : mapping.getCustomCondition().toString());
        if (method != null) {
            entity.setF_handler_method(method.getMethod().toString());
        }
        entity.setF_is_log(
                "[]".equals(entity.getF_methods()) || "[GET]".equals(entity.getF_methods()) ? Enums.FALSE
                        : Enums.TRUE);
        entity.setF_is_auto(Enums.TRUE);

        entity.setF_id(entity.generateF_id());

        return entity;
    }
}
