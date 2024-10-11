package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LevelDeposits.
 */
@Entity
@Table(name = "level_deposits")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LevelDeposits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "min_price")
    private Long minPrice;

    @Size(max = 50)
    @Column(name = "update_name", length = 50)
    private String updateName;

    @Column(name = "update_time")
    private Instant updateTime;

    @Size(max = 50)
    @Column(name = "article_code", length = 50)
    private String articleCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LevelDeposits id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public LevelDeposits code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getMinPrice() {
        return this.minPrice;
    }

    public LevelDeposits minPrice(Long minPrice) {
        this.setMinPrice(minPrice);
        return this;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public String getUpdateName() {
        return this.updateName;
    }

    public LevelDeposits updateName(String updateName) {
        this.setUpdateName(updateName);
        return this;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public LevelDeposits updateTime(Instant updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getArticleCode() {
        return this.articleCode;
    }

    public LevelDeposits articleCode(String articleCode) {
        this.setArticleCode(articleCode);
        return this;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LevelDeposits)) {
            return false;
        }
        return getId() != null && getId().equals(((LevelDeposits) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LevelDeposits{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", minPrice=" + getMinPrice() +
            ", updateName='" + getUpdateName() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", articleCode='" + getArticleCode() + "'" +
            "}";
    }
}
