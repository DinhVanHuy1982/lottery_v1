package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RandomResults.
 */
@Entity
@Table(name = "random_results")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RandomResults implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "random_date")
    private Instant randomDate;

    @Size(max = 50)
    @Column(name = "prize_code", length = 50)
    private String prizeCode;

    @Size(max = 100)
    @Column(name = "result", length = 100)
    private String result;

    @Column(name = "random_user_play")
    private Long randomUserPlay;

    @Column(name = "user_play")
    private Long userPlay;

    @Column(name = "random_user_success")
    private Long randomUserSuccess;

    @Column(name = "user_success")
    private Long userSuccess;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RandomResults id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRandomDate() {
        return this.randomDate;
    }

    public RandomResults randomDate(Instant randomDate) {
        this.setRandomDate(randomDate);
        return this;
    }

    public void setRandomDate(Instant randomDate) {
        this.randomDate = randomDate;
    }

    public String getPrizeCode() {
        return this.prizeCode;
    }

    public RandomResults prizeCode(String prizeCode) {
        this.setPrizeCode(prizeCode);
        return this;
    }

    public void setPrizeCode(String prizeCode) {
        this.prizeCode = prizeCode;
    }

    public String getResult() {
        return this.result;
    }

    public RandomResults result(String result) {
        this.setResult(result);
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getRandomUserPlay() {
        return this.randomUserPlay;
    }

    public RandomResults randomUserPlay(Long randomUserPlay) {
        this.setRandomUserPlay(randomUserPlay);
        return this;
    }

    public void setRandomUserPlay(Long randomUserPlay) {
        this.randomUserPlay = randomUserPlay;
    }

    public Long getUserPlay() {
        return this.userPlay;
    }

    public RandomResults userPlay(Long userPlay) {
        this.setUserPlay(userPlay);
        return this;
    }

    public void setUserPlay(Long userPlay) {
        this.userPlay = userPlay;
    }

    public Long getRandomUserSuccess() {
        return this.randomUserSuccess;
    }

    public RandomResults randomUserSuccess(Long randomUserSuccess) {
        this.setRandomUserSuccess(randomUserSuccess);
        return this;
    }

    public void setRandomUserSuccess(Long randomUserSuccess) {
        this.randomUserSuccess = randomUserSuccess;
    }

    public Long getUserSuccess() {
        return this.userSuccess;
    }

    public RandomResults userSuccess(Long userSuccess) {
        this.setUserSuccess(userSuccess);
        return this;
    }

    public void setUserSuccess(Long userSuccess) {
        this.userSuccess = userSuccess;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RandomResults)) {
            return false;
        }
        return getId() != null && getId().equals(((RandomResults) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RandomResults{" +
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
