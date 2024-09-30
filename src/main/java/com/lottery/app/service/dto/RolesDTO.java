package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.Roles} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RolesDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String name;

    @Size(max = 50)
    private String code;

    private Long status;

    @Size(max = 500)
    private String description;

    private Instant createTime;

    @Size(max = 50)
    private String createName;

    @Size(max = 50)
    private String updateName;

    private Instant updateTime;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RolesDTO)) {
            return false;
        }

        RolesDTO rolesDTO = (RolesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rolesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RolesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", status=" + getStatus() +
            ", description='" + getDescription() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", createName='" + getCreateName() + "'" +
            ", updateName='" + getUpdateName() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
