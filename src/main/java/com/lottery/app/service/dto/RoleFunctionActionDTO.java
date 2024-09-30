package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.RoleFunctionAction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoleFunctionActionDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String roleFunctionCode;

    @Size(max = 50)
    private String actionCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleFunctionCode() {
        return roleFunctionCode;
    }

    public void setRoleFunctionCode(String roleFunctionCode) {
        this.roleFunctionCode = roleFunctionCode;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleFunctionActionDTO)) {
            return false;
        }

        RoleFunctionActionDTO roleFunctionActionDTO = (RoleFunctionActionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roleFunctionActionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleFunctionActionDTO{" +
            "id=" + getId() +
            ", roleFunctionCode='" + getRoleFunctionCode() + "'" +
            ", actionCode='" + getActionCode() + "'" +
            "}";
    }
}
