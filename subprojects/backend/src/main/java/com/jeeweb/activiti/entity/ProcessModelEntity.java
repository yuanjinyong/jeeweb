/**
 * 
 */
package com.jeeweb.activiti.entity;

import java.util.Map;

import org.activiti.engine.impl.persistence.entity.ModelEntity;

import com.jeeweb.framework.business.entity.BaseEntity;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.core.utils.JsonUtil;

/**
 * @author 袁进勇
 *
 */
public class ProcessModelEntity extends BaseEntity<String> {
    private static final long serialVersionUID = -8051492460791490746L;

    private String description;

    private ModelEntity model;

    public ProcessModelEntity() {
        super();
    }

    public ProcessModelEntity(ModelEntity model) {
        super();
        setModel(model);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public org.activiti.engine.impl.persistence.entity.ModelEntity getModel() {
        return model;
    }

    public void setModel(org.activiti.engine.impl.persistence.entity.ModelEntity model) {
        this.model = model;
        super.setF_id(model.getId());
        if (!HelpUtil.isEmpty(model.getMetaInfo())) {
            Map<String, Object> metaInfMap = JsonUtil.toMap(model.getMetaInfo());
            this.description = (String) metaInfMap.get("description");
        }
    }
}
