package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.RandomResults} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RandomResultsDTO implements Serializable {

    private Long id;

    private Instant randomDate;

    @Size(max = 50)
    private String prizeCode;

    @Size(max = 100)
    private String result;

    private Long randomUserPlay;

    private Long userPlay;

    private Long randomUserSuccess;

    private Long userSuccess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRandomDate() {
        return randomDate;
    }

    public void setRandomDate(Instant randomDate) {
        this.randomDate = randomDate;
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

    public Long getRandomUserPlay() {
        return randomUserPlay;
    }

    public void setRandomUserPlay(Long randomUserPlay) {
        this.randomUserPlay = randomUserPlay;
    }

    public Long getUserPlay() {
        return userPlay;
    }

    public void setUserPlay(Long userPlay) {
        this.userPlay = userPlay;
    }

    public Long getRandomUserSuccess() {
        return randomUserSuccess;
    }

    public void setRandomUserSuccess(Long randomUserSuccess) {
        this.randomUserSuccess = randomUserSuccess;
    }

    public Long getUserSuccess() {
        return userSuccess;
    }

    public void setUserSuccess(Long userSuccess) {
        this.userSuccess = userSuccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RandomResultsDTO)) {
            return false;
        }

        RandomResultsDTO randomResultsDTO = (RandomResultsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, randomResultsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RandomResultsDTO{" +
            "id=" + getId() +
            ", randomDate='" + getRandomDate() + "'" +
            ", prizeCode='" + getPrizeCode() + "'" +
            ", result='" + getResult() + "'" +
            ", randomUserPlay=" + getRandomUserPlay() +
            ", userPlay=" + getUserPlay() +
            ", randomUserSuccess=" + getRandomUserSuccess() +
            ", userSuccess=" + getUserSuccess() +
            "}";
    }
}
