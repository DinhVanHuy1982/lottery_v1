package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LevelDepositsResult.
 */
@Entity
@Table(name = "level_deposits_result")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LevelDepositsResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "code", length = 50)
    private String code;

    @Size(max = 50)
    @Column(name = "level_deposits_code", length = 50)
    private String levelDepositsCode;

    @Size(max = 50)
    @Column(name = "random_result_code", length = 50)
    private String randomResultCode;

    @Column(name = "result_date")
    private Instant resultDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LevelDepositsResult id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public LevelDepositsResult code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevelDepositsCode() {
        return this.levelDepositsCode;
    }

    public LevelDepositsResult levelDepositsCode(String levelDepositsCode) {
        this.setLevelDepositsCode(levelDepositsCode);
        return this;
    }

    public void setLevelDepositsCode(String levelDepositsCode) {
        this.levelDepositsCode = levelDepositsCode;
    }

    public String getRandomResultCode() {
        return this.randomResultCode;
    }

    public LevelDepositsResult randomResultCode(String randomResultCode) {
        this.setRandomResultCode(randomResultCode);
        return this;
    }

    public void setRandomResultCode(String randomResultCode) {
        this.randomResultCode = randomResultCode;
    }

    public Instant getResultDate() {
        return this.resultDate;
    }

    public LevelDepositsResult resultDate(Instant resultDate) {
        this.setResultDate(resultDate);
        return this;
    }

    public void setResultDate(Instant resultDate) {
        this.resultDate = resultDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelDepositsResult)) {
            return false;
        }
        return getId() != null && getId().equals(((LevelDepositsResult) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelDepositsResult{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", levelDepositsCode='" + getLevelDepositsCode() + "'" +
            ", randomResultCode='" + getRandomResultCode() + "'" +
            ", resultDate='" + getResultDate() + "'" +
            "}";
    }
}
