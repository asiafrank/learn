package com.example.appoperation.db.po;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;

public class OperationResourcePO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private LocalDateTime effectBegin;
    private LocalDateTime effectEnd;
    private Integer resourceLocationId;
    private String bannerUrl;
    private String coverUrl;
    private String displayAliases;
    private Boolean haveFlash;
    private String qrcodeTitle;
    private String soundUrl;
    private String imgHighScreen;
    private String imgNormalScreen;
    private String imgWideScreen;
    private Integer protocolType;
    private String protocolContent;
    private String commonParam;
    private String comments;
    private String operateUser;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getEffectBegin() {
        return effectBegin;
    }

    public void setEffectBegin(LocalDateTime effectBegin) {
        this.effectBegin = effectBegin;
    }

    public LocalDateTime getEffectEnd() {
        return effectEnd;
    }

    public void setEffectEnd(LocalDateTime effectEnd) {
        this.effectEnd = effectEnd;
    }

    public Integer getResourceLocationId() {
        return resourceLocationId;
    }

    public void setResourceLocationId(Integer resourceLocationId) {
        this.resourceLocationId = resourceLocationId;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDisplayAliases() {
        return displayAliases;
    }

    public void setDisplayAliases(String displayAliases) {
        this.displayAliases = displayAliases;
    }

    public Boolean getHaveFlash() {
        return haveFlash;
    }

    public void setHaveFlash(Boolean haveFlash) {
        this.haveFlash = haveFlash;
    }

    public String getQrcodeTitle() {
        return qrcodeTitle;
    }

    public void setQrcodeTitle(String qrcodeTitle) {
        this.qrcodeTitle = qrcodeTitle;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }

    public String getImgHighScreen() {
        return imgHighScreen;
    }

    public void setImgHighScreen(String imgHighScreen) {
        this.imgHighScreen = imgHighScreen;
    }

    public String getImgNormalScreen() {
        return imgNormalScreen;
    }

    public void setImgNormalScreen(String imgNormalScreen) {
        this.imgNormalScreen = imgNormalScreen;
    }

    public String getImgWideScreen() {
        return imgWideScreen;
    }

    public void setImgWideScreen(String imgWideScreen) {
        this.imgWideScreen = imgWideScreen;
    }

    public Integer getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(Integer protocolType) {
        this.protocolType = protocolType;
    }

    public String getProtocolContent() {
        return protocolContent;
    }

    public void setProtocolContent(String protocolContent) {
        this.protocolContent = protocolContent;
    }

    public String getCommonParam() {
        return commonParam;
    }

    public void setCommonParam(String commonParam) {
        this.commonParam = commonParam;
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
