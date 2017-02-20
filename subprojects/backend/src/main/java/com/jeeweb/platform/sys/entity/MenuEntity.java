package com.jeeweb.platform.sys.entity;

import java.util.List;

import com.jeeweb.framework.business.entity.TreeNodeEntity;
import com.jeeweb.framework.core.model.RowMap;

public class MenuEntity extends TreeNodeEntity<String, MenuEntity> {
    public static final String MENU_ROOT = "ROOT";
    private String f_name; // 菜单名称
    private String f_desc; // 菜单描述
    private String f_icon; // 图标
    private Integer f_type; // 类型，1、目录；2、页面；3、按钮
    private String f_url_id; // 菜单对应的URL，目录和按钮不需要填写，只有对应页面的菜单才需要填写
    private UrlEntity url;
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

    public void setF_desc(String f_remark) {
        this.f_desc = f_remark;
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

    public String getF_url_id() {
        return f_url_id;
    }

    public void setF_url_id(String f_url_id) {
        this.f_url_id = f_url_id;
    }

    public UrlEntity getUrl() {
        return url;
    }

    public void setF_url(UrlEntity url) {
        this.url = url;
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
