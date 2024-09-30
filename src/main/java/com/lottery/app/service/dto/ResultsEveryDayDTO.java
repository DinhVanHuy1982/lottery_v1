package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.ResultsEveryDay} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResultsEveryDayDTO implements Serializable {

    private Long id;

    private Instant resultDate;

    @Size(max = 50)
    private String prizeCode;

    @Size(max = 100)
    private String result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getResultDate() {
        return resultDate;
    }

    public void setResultDate(Instant resultDate) {
        this.resultDate = resultDate;
    }

    public String getPrizeCode() {
        return prizeCode;
    }

    public void setPrizeCode(String prizeCode) {
        this.prizeCode = prizeCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultsEveryDayDTO)) {
            return false;
        }

        ResultsEveryDayDTO resultsEveryDayDTO = (ResultsEveryDayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resultsEveryDayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultsEveryDayDTO{" +
            "id=" + getId() +
            ", resultDate='" + getResultDate() + "'" +
            ", prizeCode='" + getPrizeCode() + "'" +
            ", result='" + getResult() + "'" +
            "}";
    }
}
