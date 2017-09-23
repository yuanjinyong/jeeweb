package com.jeeweb.framework.business.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.entity.TreeNodeEntity;
import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.model.IAttachment;
import com.jeeweb.framework.business.model.IAuditor;
import com.jeeweb.framework.business.model.ICreator;
import com.jeeweb.framework.business.model.IModifier;
import com.jeeweb.framework.business.model.IPreset;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.service.SuperService;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.pub.entity.AttachmentEntity;
import com.jeeweb.platform.pub.mapper.AttachmentMapper;
import com.jeeweb.platform.security.utils.SecurityUtil;
import com.jeeweb.platform.sys.entity.UserEntity;

public abstract class BaseService<P, E> extends SuperService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected AttachmentMapper attachmentMapper;

    protected abstract BaseMapper<P, E> getMapper();

    @Transactional(readOnly = true)
    public E selectEntity(P primaryKey) {
        E entity = getMapper().selectEntity(primaryKey);
        selectAttachmentList(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public List<E> selectEntityListPage(ParameterMap params) {
        return getMapper().selectEntityListPage(params);
    }

    @Transactional(readOnly = true)
    public List<RowMap> selectMapEntityListPage(ParameterMap params) {
        return getMapper().selectMapEntityListPage(params);
    }

    public void insertEntity(E entity) {
        fillCreator(entity);
        fillPreset(entity);
        getMapper().insertEntity(entity);

        insertAttachmentList(entity);
    }

    public void insertEntities(List<E> entityList) {
        for (E entity : entityList) {
            fillCreator(entity);
            fillPreset(entity);
        }
        getMapper().insertEntities(entityList);
    }

    public void updateEntity(P primaryKey, E entity) {
        fillModifier(entity);
        updateAttachmentList(primaryKey, entity);

        getMapper().updateEntity(entity);
    }

    public void deleteEntity(P primaryKey) {
        E entity = getMapper().selectEntity(primaryKey);

        validateDeleteEntity(entity);
        if (getMapper().isCanDeleteEntity(primaryKey) > 0) {
            throw new BusinessException("存在关联数据，不能删除！");
        }

        beforeDeleteEntity(entity);

        if (entity instanceof TreeNodeEntity) {
            // 删除孩子
            TreeNodeEntity<?, ?> node = (TreeNodeEntity<?, ?>) entity;
            getMapper().deleteEntities(new ParameterMap("f_parent_path_like", node.getF_full_path()));
        }

        deleteAttachmentList(primaryKey, entity);
        getMapper().deleteEntity(primaryKey);

        afterDeleteEntity(entity);
    }

    protected void validateDeleteEntity(E entity) {
        checkTreeRootNode(entity);
        checkPreset(entity);
    }

    protected void beforeDeleteEntity(E entity) {
    }

    protected void afterDeleteEntity(E entity) {
    }

    public void deleteEntities(ParameterMap params) {
        validateDeleteEntities(params);
        beforeDeleteEntities(params);

        getMapper().deleteEntities(params);

        afterDeleteEntities(params);
    }

    protected void validateDeleteEntities(ParameterMap params) {
    }

    protected void beforeDeleteEntities(ParameterMap params) {
    }

    protected void afterDeleteEntities(ParameterMap params) {
    }

    protected void fillModifier(E entity) {
        if (entity instanceof IModifier) {
            UserEntity user = SecurityUtil.getCurUser();
            IModifier modifier = (IModifier) entity;
            modifier.setF_modifier_id(user.getF_id());
            modifier.setF_modifier_name(user.getF_name());
            modifier.setF_modified_time(HelpUtil.getNowTime());
        }
    }

    protected <T> void fillCreator(T entity) {
        if (entity instanceof ICreator) {
            UserEntity user = SecurityUtil.getCurUser();
            ICreator creator = (ICreator) entity;
            creator.setF_creator_id(user.getF_id());
            creator.setF_creator_name(user.getF_name());
            creator.setF_created_time(HelpUtil.getNowTime());
        }
    }

    protected void fillPreset(E entity) {
        if (entity instanceof IPreset) {
            IPreset preset = (IPreset) entity;
            preset.setF_is_preset(IPreset.NO); // 系统预置数据是不能通过程序来修改的，这里能够写入的数据都不是系统预置的。
        }
    }

    protected List<AttachmentEntity> selectAttachmentList(E entity) {
        if (entity instanceof IAttachment) {
            IAttachment attachment = (IAttachment) entity;
            String f_id_in = attachment.getF_attachment_ids();
            if (!HelpUtil.isEmpty(f_id_in)) {
                ParameterMap params = new ParameterMap("f_id_in", f_id_in).put("f_status_in", "2,3");
                attachment.setAttachmentList(attachmentMapper.selectEntityListPage(params));
            }

            return attachment.getAttachmentList();
        }

        return null;
    }

    protected void insertAttachmentList(E entity) {
        if (entity instanceof IAttachment) {
            IAttachment businessEntity = (IAttachment) entity;
            String f_id_in = businessEntity.getF_attachment_ids();
            if (!HelpUtil.isEmpty(f_id_in)) {
                ParameterMap params = new ParameterMap("f_id_in", f_id_in).put("f_status_in", "1,4");
                List<AttachmentEntity> attachmentList = attachmentMapper.selectEntityListPage(params);
                for (AttachmentEntity attachmentEntity : attachmentList) {
                    if (!HelpUtil.isEmpty(attachmentEntity.getF_local_path())) {
                        attachmentEntity.setF_entity_name(businessEntity.getClass().getName());
                        attachmentEntity.setF_entity_id(businessEntity.getF_id());
                        attachmentEntity.setF_status(AttachmentEntity.STATUS_ARCHIVED);
                    }
                    if (!HelpUtil.isEmpty(attachmentEntity.getF_remote_path())) {
                        attachmentEntity.setF_status(AttachmentEntity.STATUS_UPLOADED);
                    }
                    attachmentMapper.updateEntity(attachmentEntity);
                }
                businessEntity.setAttachmentList(attachmentList);
            } else {
                businessEntity.setAttachmentList(null);
            }
        }
    }

    protected void updateAttachmentList(P primaryKey, E entity) {
        deleteAttachmentList(primaryKey, entity);
        insertAttachmentList(entity);
    }

    protected void deleteAttachmentList(P primaryKey, E entity) {
        if (entity instanceof IAttachment) {
            List<AttachmentEntity> attachmentList = selectAttachmentList(selectEntity(primaryKey));
            if (!HelpUtil.isEmpty(attachmentList)) {
                for (AttachmentEntity attachmentEntity : attachmentList) {
                    attachmentEntity.setF_status(AttachmentEntity.STATUS_PREDELETE);
                    attachmentMapper.updateEntity(attachmentEntity);
                }
            }
        }
    }

    protected void checkTreeRootNode(E entity) {
        if (entity instanceof TreeNodeEntity) {
            @SuppressWarnings("rawtypes")
            TreeNodeEntity treeNode = (TreeNodeEntity) entity;
            if (treeNode.getF_parent_id() == null) {
                throw new BusinessException("系统预置的根节点不能删除！");
            }
        }
    }

    protected void checkPreset(E entity) {
        if (entity instanceof IPreset) {
            IPreset preset = (IPreset) entity;
            if (preset.getF_is_preset() == IPreset.YES) {
                throw new BusinessException("系统预置数据，不能删除！");
            }
        }
    }

    public void auditEntity(P primaryKey, E entity) {
        if (!(entity instanceof IAuditor)) {
            return;
        }

        E oldEntity = getMapper().selectEntity(primaryKey);
        IAuditor auditor = (IAuditor) entity;
        IAuditor oldAuditor = (IAuditor) oldEntity;
        if (oldAuditor.getF_status() != IAuditor.STATUS_NEW && oldAuditor.getF_status() != IAuditor.STATUS_REJECTED) {
            throw new BusinessException("只有未审核和审核未通过的记录才可进行审核！");
        }
        if (auditor.getF_status() == IAuditor.STATUS_REJECTED && HelpUtil.isEmpty(auditor.getF_audited_comments())) {
            throw new BusinessException("审不通过时，必须填写审核意见！");
        }
        checkAuditor(oldEntity);

        UserEntity user = SecurityUtil.getCurUser();
        oldAuditor.setF_auditor_id(user.getF_id());
        oldAuditor.setF_auditor_name(user.getF_name());
        oldAuditor.setF_audited_time(HelpUtil.getNowTime());
        oldAuditor.setF_status(auditor.getF_status());
        oldAuditor.setF_audited_comments(auditor.getF_audited_comments());
        getMapper().updateEntity(oldEntity);
    }

    protected void checkAuditor(E oldEntity) {
    }
}
