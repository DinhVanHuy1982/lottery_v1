package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.LevelDepositsResult} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LevelDepositsResultDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String code;

    @Size(max = 50)
    private String levelDepositsCode;

    @Size(max = 50)
    private String randomResultCode;

    private Instant resultDate;

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

    public String getLevelDepositsCode() {
        return levelDepositsCode;
    }

    public void setLevelDepositsCode(String levelDepositsCode) {
        this.levelDepositsCode = levelDepositsCode;
    }

    public String getRandomResultCode() {
        return randomResultCode;
    }

    public void setRandomResultCode(String randomResultCode) {
        this.randomResultCode = randomResultCode;
    }

    public Instant getResultDate() {
        return resultDate;
    }

    public void setResultDate(Instant resultDate) {
        this.resultDate = resultDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelDepositsResultDTO)) {
            return false;
        }

        LevelDepositsResultDTO levelDepositsResultDTO = (LevelDepositsResultDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, levelDepositsResultDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelDepositsResultDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", levelDepositsCode='" + getLevelDepositsCode() + "'" +
            ", randomResultCode='" + getRandomResultCode() + "'" +
            ", resultDate='" + getResultDate() + "'" +
            "}";
    }
}
