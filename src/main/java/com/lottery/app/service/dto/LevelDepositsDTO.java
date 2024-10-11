package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.LevelDeposits} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LevelDepositsDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String code;

    private Long minPrice;

    @Size(max = 50)
    private String updateName;

    private Instant updateTime;

    @Size(max = 50)
    private String articleCode;

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

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
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

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelDepositsDTO)) {
            return false;
        }

        LevelDepositsDTO levelDepositsDTO = (LevelDepositsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, levelDepositsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelDepositsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", minPrice=" + getMinPrice() +
            ", updateName='" + getUpdateName() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", articleCode='" + getArticleCode() + "'" +
            "}";
    }
}
