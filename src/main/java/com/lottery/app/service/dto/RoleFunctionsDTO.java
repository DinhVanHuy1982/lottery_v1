package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.RoleFunctions} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoleFunctionsDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String code;

    @Size(max = 50)
    private String roleCode;

    @Size(max = 50)
    private String functionCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleFunctionsDTO)) {
            return false;
        }

        RoleFunctionsDTO roleFunctionsDTO = (RoleFunctionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roleFunctionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleFunctionsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", roleCode='" + getRoleCode() + "'" +
            ", functionCode='" + getFunctionCode() + "'" +
            "}";
    }
}
