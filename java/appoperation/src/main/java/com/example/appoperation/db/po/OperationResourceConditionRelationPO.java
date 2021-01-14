package com.example.appoperation.db.po;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.time.LocalDateTime;

public class OperationResourceConditionRelationPO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer operationResourceId;
    private Integer classificationConditionId;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperationResourceId() {
        return operationResourceId;
    }

    public void setOperationResourceId(Integer operationResourceId) {
        this.operationResourceId = operationResourceId;
    }

    public Integer getClassificationConditionId() {
        return classificationConditionId;
    }

    public void setClassificationConditionId(Integer classificationConditionId) {
        this.classificationConditionId = classificationConditionId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
