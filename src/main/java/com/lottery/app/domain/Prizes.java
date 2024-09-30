package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Prizes.
 */
@Entity
@Table(name = "prizes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prizes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "code", length = 50)
    private String code;

    @Size(max = 50)
    @Column(name = "article_code", length = 50)
    private String articleCode;

    @Size(max = 50)
    @Column(name = "level_cup", length = 50)
    private String levelCup;

    @Column(name = "number_prize")
    private Long numberPrize;

    @Column(name = "create_time")
    private Instant createTime;

    @Size(max = 50)
    @Column(name = "create_name", length = 50)
    private String createName;

    @Column(name = "update_time")
    private Instant updateTime;

    @Size(max = 50)
    @Column(name = "update_name", length = 50)
    private String updateName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prizes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Prizes code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getArticleCode() {
        return this.articleCode;
    }

    public Prizes articleCode(String articleCode) {
        this.setArticleCode(articleCode);
        return this;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getLevelCup() {
        return this.levelCup;
    }

    public Prizes levelCup(String levelCup) {
        this.setLevelCup(levelCup);
        return this;
    }

    public void setLevelCup(String levelCup) {
        this.levelCup = levelCup;
    }

    public Long getNumberPrize() {
        return this.numberPrize;
    }

    public Prizes numberPrize(Long numberPrize) {
        this.setNumberPrize(numberPrize);
        return this;
    }

    public void setNumberPrize(Long numberPrize) {
        this.numberPrize = numberPrize;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public Prizes createTime(Instant createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getCreateName() {
        return this.createName;
    }

    public Prizes createName(String createName) {
        this.setCreateName(createName);
        return this;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public Prizes updateTime(Instant updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateName() {
        return this.updateName;
    }

    public Prizes updateName(String updateName) {
        this.setUpdateName(updateName);
        return this;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prizes)) {
            return false;
        }
        return getId() != null && getId().equals(((Prizes) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prizes{" +
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
