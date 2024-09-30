package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.Prizes} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrizesDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String code;

    @Size(max = 50)
    private String articleCode;

    @Size(max = 50)
    private String levelCup;

    private Long numberPrize;

    private Instant createTime;

    @Size(max = 50)
    private String createName;

    private Instant updateTime;

    @Size(max = 50)
    private String updateName;

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

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getLevelCup() {
        return levelCup;
    }

    public void setLevelCup(String levelCup) {
        this.levelCup = levelCup;
    }

    public Long getNumberPrize() {
        return numberPrize;
    }

    public void setNumberPrize(Long numberPrize) {
        this.numberPrize = numberPrize;
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

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrizesDTO)) {
            return false;
        }

        PrizesDTO prizesDTO = (PrizesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prizesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrizesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", articleCode='" + getArticleCode() + "'" +
            ", levelCup='" + getLevelCup() + "'" +
            ", numberPrize=" + getNumberPrize() +
            ", createTime='" + getCreateTime() + "'" +
            ", createName='" + getCreateName() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateName='" + getUpdateName() + "'" +
            "}";
    }
}
