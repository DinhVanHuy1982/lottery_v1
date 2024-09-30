package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.AppParams} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppParamsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String code;

    @Size(max = 150)
    private String value;

    @Size(max = 50)
    private String type;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppParamsDTO)) {
            return false;
        }

        AppParamsDTO appParamsDTO = (AppParamsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appParamsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppParamsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", value='" + getValue() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
