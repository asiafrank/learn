package com.example.appoperation.db.po;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;

public class OperationResourceLocationPO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String resourceName;
    private Boolean appType;
    private Boolean resourceType;
    private Integer carouselNum;
    private String comments;
    private String operateUser;
    private String imageSize;
    private String imageExtension;
    private Boolean hasCover;
    private Boolean hasAlias;
    private Boolean hasBgm;
    private Boolean hasAnimation;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Boolean getAppType() {
        return appType;
    }

    public void setAppType(Boolean appType) {
        this.appType = appType;
    }

    public Boolean getResourceType() {
        return resourceType;
    }

    public void setResourceType(Boolean resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getCarouselNum() {
        return carouselNum;
    }

    public void setCarouselNum(Integer carouselNum) {
        this.carouselNum = carouselNum;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }

    public Boolean getHasCover() {
        return hasCover;
    }

    public void setHasCover(Boolean hasCover) {
        this.hasCover = hasCover;
    }

    public Boolean getHasAlias() {
        return hasAlias;
    }

    public void setHasAlias(Boolean hasAlias) {
        this.hasAlias = hasAlias;
    }

    public Boolean getHasBgm() {
        return hasBgm;
    }

    public void setHasBgm(Boolean hasBgm) {
        this.hasBgm = hasBgm;
    }

    public Boolean getHasAnimation() {
        return hasAnimation;
    }

    public void setHasAnimation(Boolean hasAnimation) {
        this.hasAnimation = hasAnimation;
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
