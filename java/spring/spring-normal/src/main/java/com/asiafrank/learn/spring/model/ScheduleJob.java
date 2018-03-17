package com.asiafrank.learn.spring.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ScheduleJob
 * <p>
 * </p>
 * Created at 12/26/2016.
 *
 * @author asiafrank
 */
@Document
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ScheduleJob implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    /**
     * Verified by {@link org.quartz.CronExpression}
     * Ref: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/tutorial-lesson-06.html
     *
     * @see com.asiafrank.learn.spring.info.CronInfo
     */
    private String cron;

    /**
     * Format:
     * <pre>
     *     {name}
     * </pre>
     * Used by {@link org.quartz.JobDetail}
     */
    private String identityName;

    /**
     * Format:
     * <pre>
     *     {name}-group
     * </pre>
     * Used by {@link org.quartz.JobDetail}
     */
    private String identityGroup;

    /**
     * Format:
     * <pre>
     *     {name}-trigger
     * </pre>
     * Used by {@link org.quartz.CronTrigger}
     */
    private String triggerName;

    /**
     * Format:
     * <pre>
     *     {name}-trigger-group
     * </pre>
     * Used by {@link org.quartz.CronTrigger}
     */
    private String triggerGroup;

    private List<Sample> samples;

    private boolean enable;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    public ScheduleJob(String name, String cron, List<Sample> samples, boolean enable) {
        Assert.notNull(name);
        Assert.notNull(cron);
        Assert.notEmpty(samples);
        Assert.notNull(enable);

        this.name = name;
        this.cron = cron;
        this.samples = samples;
        this.enable = enable;

        this.identityName = name;
        this.identityGroup = name + "-group";
        this.triggerName = name + "-trigger";
        this.triggerGroup = name + "-trigger-group";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getIdentityGroup() {
        return identityGroup;
    }

    public void setIdentityGroup(String identityGroup) {
        this.identityGroup = identityGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
