package com.jeeweb.framework.business.web.api;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.jeeweb.framework.business.entity.BaseEntity;
import com.jeeweb.framework.business.entity.TreeNodeEntity;
import com.jeeweb.framework.business.model.IAttachment;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.business.web.controller.SuperController;
import com.jeeweb.framework.core.model.Page;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.core.utils.TreeUtil;
import com.jeeweb.platform.pub.entity.AttachmentEntity;

public abstract class BaseApi<P, E extends BaseEntity<P>> extends SuperController {
    protected abstract BaseService<P, E> getService();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected ResponseResult treeEntities() {
        ParameterMap params = $params();
        List entities = getService().selectEntityListPage(params);
        Page<TreeNodeEntity> page = params.page(entities);
        page.setItems(TreeUtil.listToTree(entities));
        if (!$bool("tree", false)) {
            page.setItems(TreeUtil.treeToList(page.getItems()));
        }
        return new ResponseResult(new Result(page), HttpStatus.OK);
    }

    protected ResponseResult listEntities() {
        ParameterMap params = $params();
        List<E> entities = getService().selectEntityListPage(params);
        return new ResponseResult(new Result(params.page(entities)), HttpStatus.OK);
    }

    protected ResponseResult createEntity(E entity, UriComponentsBuilder ucBuilder) {
        getService().insertEntity(entity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(entity.getF_id()).toUri());
        return new ResponseResult(new Result(entity), headers, HttpStatus.CREATED);
    }

    protected ResponseResult getEntity(P primaryKey) {
        E entity = getService().selectEntity(primaryKey);
        fillAttachment(entity);

        return new ResponseResult(new Result(entity), HttpStatus.OK);
    }

    protected ResponseResult updateEntity(P primaryKey, E entity) {
        getService().updateEntity(primaryKey, entity);
        return new ResponseResult(new Result(entity), HttpStatus.OK);
    }

    protected ResponseResult deleteEntity(P primaryKey) {
        getService().deleteEntity(primaryKey);

        // 这里我们把执行结果通过state和msg字段返回给客户端，所以返回的状态码从204改为200。
        // 返回码具体意义请参考HTTP协议：https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
        // return new ResponseEntity<MapEntity>(success(), HttpStatus.NO_CONTENT);
        return new ResponseResult(new Result(), HttpStatus.OK);
    }

    protected ResponseResult deleteEntities() {
        getService().deleteEntities(new ParameterMap("f_id_in", $("f_id_in")));

        // 这里我们把执行结果通过state和msg字段返回给客户端，所以返回的状态码从204改为200。
        // 返回码具体意义请参考HTTP协议：https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
        // return new ResponseEntity<MapEntity>(success(), HttpStatus.NO_CONTENT);
        return new ResponseResult(new Result(), HttpStatus.OK);
    }

    protected ResponseResult auditEntity(P primaryKey, E entity) {
        getService().auditEntity(primaryKey, entity);
        return new ResponseResult(new Result(entity), HttpStatus.OK);
    }

    protected void fillAttachment(E entity) {
        if (entity instanceof IAttachment) {
            List<AttachmentEntity> attachmentList = ((IAttachment) entity).getAttachmentList();
            if (!HelpUtil.isEmpty(attachmentList)) {
                String url = buildUrl();
                for (AttachmentEntity attachmentEntity : attachmentList) {
                    attachmentEntity.setName(attachmentEntity.getF_name());
                    attachmentEntity.setUrl(String.format(ATTACHMENT_URL_FORMAT, url, attachmentEntity.getF_id()));
                }
            }
        }
    }
}
