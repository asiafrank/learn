package com.example.appoperation.db.po;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;

public class UserClassificationConditionPO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String shortExpression;
    private String fullExpression;
    private LocalDateTime effectiveTime;
    private Boolean appType;
    private String operateUser;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Boolean status;
    private Boolean usedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortExpression() {
        return shortExpression;
    }

    public void setShortExpression(String shortExpression) {
        this.shortExpression = shortExpression;
    }

    public String getFullExpression() {
        return fullExpression;
    }

    public void setFullExpression(String fullExpression) {
        this.fullExpression = fullExpression;
    }

    public LocalDateTime getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(LocalDateTime effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Boolean getAppType() {
        return appType;
    }

    public void setAppType(Boolean appType) {
        this.appType = appType;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
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

    public Boolean getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(Boolean usedBy) {
        this.usedBy = usedBy;
    }
}
