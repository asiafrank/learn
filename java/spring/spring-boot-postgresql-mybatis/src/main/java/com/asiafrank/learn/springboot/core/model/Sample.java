package com.asiafrank.learn.springboot.core.model;

import com.asiafrank.learn.springboot.util.Type;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Sample
 * <p>
 * </p>
 * Created at 1/11/2017.
 *
 * @author zhangxf
 */
@Alias("Sample")
public class Sample implements Serializable {
    private static final long serialVersionUID = -1L;
    private Long id;

    private String name;

    private Type type;

    private String description;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
