package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResultsEveryDay.
 */
@Entity
@Table(name = "results_every_day")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResultsEveryDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "result_date")
    private Instant resultDate;

    @Size(max = 50)
    @Column(name = "prize_code", length = 50)
    private String prizeCode;

    @Size(max = 100)
    @Column(name = "result", length = 100)
    private String result;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResultsEveryDay id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getResultDate() {
        return this.resultDate;
    }

    public ResultsEveryDay resultDate(Instant resultDate) {
        this.setResultDate(resultDate);
        return this;
    }

    public void setResultDate(Instant resultDate) {
        this.resultDate = resultDate;
    }

    public String getPrizeCode() {
        return this.prizeCode;
    }

    public ResultsEveryDay prizeCode(String prizeCode) {
        this.setPrizeCode(prizeCode);
        return this;
    }

    public void setPrizeCode(String prizeCode) {
        this.prizeCode = prizeCode;
    }

    public String getResult() {
        return this.result;
    }

    public ResultsEveryDay result(String result) {
        this.setResult(result);
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultsEveryDay)) {
            return false;
        }
        return getId() != null && getId().equals(((ResultsEveryDay) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultsEveryDay{" +
            "id=" + getId() +
            ", resultDate='" + getResultDate() + "'" +
            ", prizeCode='" + getPrizeCode() + "'" +
            ", result='" + getResult() + "'" +
            "}";
    }
}
