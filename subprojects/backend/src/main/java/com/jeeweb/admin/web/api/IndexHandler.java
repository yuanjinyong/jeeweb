/**
 * 
 */
package com.jeeweb.admin.web.api;

import java.util.Map;

/**
 * @author 身份认证通过后的回调处理
 *
 */
public interface IndexHandler {
    void onIndex(Map<String, Object> data);
}
