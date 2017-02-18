package com.jeeweb.framework.business.entity;

import com.jeeweb.framework.core.utils.JsonUtil;

/**
 * 所有实体类的基类
 * 
 * @author 袁进勇
 *
 */
public abstract class BaseEntity<P> {
    private P f_id; // 主键，如果某个实体类不以f_id字段作为主键，则忽略该属性即可。

    public P getF_id() {
        return f_id;
    }

    public void setF_id(P f_id) {
        this.f_id = f_id;
    }

    @Override
    public String toString() {
        return JsonUtil.toString(this);
    }
}