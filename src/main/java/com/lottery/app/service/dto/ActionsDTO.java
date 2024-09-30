package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.Actions} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActionsDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String name;

    @Size(max = 50)
    private String code;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActionsDTO)) {
            return false;
        }

        ActionsDTO actionsDTO = (ActionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, actionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActionsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
