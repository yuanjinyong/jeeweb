/**
 * 
 */
package com.jeeweb.framework.business.model;

import java.util.List;

import com.jeeweb.platform.pub.entity.AttachmentEntity;

/**
 * @author 袁进勇
 *
 */
public interface IAttachment {
    Integer getF_id();

    String getF_attachment_ids();

    void setF_attachment_ids(String f_attachment_ids);

    List<AttachmentEntity> getAttachmentList();

    void setAttachmentList(List<AttachmentEntity> attachmentList);
}
