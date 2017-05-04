package com.jeeweb.platform.sys.entity;

import java.util.List;

import com.jeeweb.framework.business.entity.TreeNodeEntity;
import com.jeeweb.framework.core.model.RowMap;

public class MenuEntity extends TreeNodeEntity<String, MenuEntity> {
    public static final String ROOT_ID = "ROOT";
    public static final String ROOT_PATH = "/ROOT/";

    private static final long serialVersionUID = -8736669826481868926L;

    private String f_name; // 菜单名称
    private String f_desc; // 菜单描述
    private String f_icon; // 图标
    private Integer f_type; // 类型，1、目录；2、页面；3、按钮；4、令牌
    private String f_route_path; // 菜单对应的URL，目录和按钮不需要填写，只有对应页面的菜单才需要填写
    private Integer f_is_web; // Web端是否启用，1启用，2不启用
    private Integer f_is_android; // Android端是否启用，1启用，2不启用
    private Integer f_is_ios; // IOS端是否启用，1启用，2不启用    private String f_remark; // 备注
    private Integer f_status; // 当前站是否启用，1、启用；2、禁用
    private String f_remark; // 备注
    private List<RowMap> urlList; // 页面或者按钮需要访问的URL地址

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_desc() {
        return f_desc;
    }

    public void setF_desc(String f_desc) {
        this.f_desc = f_desc;
    }

    public String getF_icon() {
        return f_icon;
    }

    public void setF_icon(String f_icon) {
        this.f_icon = f_icon;
    }

    public Integer getF_type() {
        return f_type;
    }

    public void setF_type(Integer f_type) {
        this.f_type = f_type;
    }

    public String getF_route_path() {
        return f_route_path;
    }

    public void setF_route_path(String f_route_path) {
        this.f_route_path = f_route_path;
    }

    public Integer getF_is_web() {
        return f_is_web;
    }

    public void setF_is_web(Integer f_is_web) {
        this.f_is_web = f_is_web;
    }

    public Integer getF_is_android() {
        return f_is_android;
    }

    public void setF_is_android(Integer f_is_android) {
        this.f_is_android = f_is_android;
    }

    public Integer getF_is_ios() {
        return f_is_ios;
    }

    public void setF_is_ios(Integer f_is_ios) {
        this.f_is_ios = f_is_ios;
    }

    public Integer getF_status() {
        return f_status;
    }

    public void setF_status(Integer f_status) {
        this.f_status = f_status;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

    public List<RowMap> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<RowMap> urlList) {
        this.urlList = urlList;
    }
}
