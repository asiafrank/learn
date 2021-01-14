package com.example.appoperation.db.po;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.time.LocalDateTime;

public class OperationResourceWeeklySortingPO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer resourceLocationId;
    private Integer operationResourceId;
    private Integer order;
    private Long weekBegin;
    private Long weekEnd;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResourceLocationId() {
        return resourceLocationId;
    }

    public void setResourceLocationId(Integer resourceLocationId) {
        this.resourceLocationId = resourceLocationId;
    }

    public Integer getOperationResourceId() {
        return operationResourceId;
    }

    public void setOperationResourceId(Integer operationResourceId) {
        this.operationResourceId = operationResourceId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getWeekBegin() {
        return weekBegin;
    }

    public void setWeekBegin(Long weekBegin) {
        this.weekBegin = weekBegin;
    }

    public Long getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(Long weekEnd) {
        this.weekEnd = weekEnd;
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
