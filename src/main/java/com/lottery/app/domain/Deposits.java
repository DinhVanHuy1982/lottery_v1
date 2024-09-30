package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Deposits.
 */
@Entity
@Table(name = "deposits")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Deposits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "article_code", length = 50)
    private String articleCode;

    @Size(max = 50)
    @Column(name = "netwrok_card", length = 50)
    private String netwrokCard;

    @Size(max = 50)
    @Column(name = "value_card", length = 50)
    private String valueCard;

    @Column(name = "create_time")
    private Instant createTime;

    @Size(max = 50)
    @Column(name = "seri_card", length = 50)
    private String seriCard;

    @Size(max = 50)
    @Column(name = "code_card", length = 50)
    private String codeCard;

    @Column(name = "status")
    private Long status;

    @Size(max = 50)
    @Column(name = "user_appose", length = 50)
    private String userAppose;

    @Size(max = 100)
    @Column(name = "value_choice", length = 100)
    private String valueChoice;

    @Size(max = 50)
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Deposits id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleCode() {
        return this.articleCode;
    }

    public Deposits articleCode(String articleCode) {
        this.setArticleCode(articleCode);
        return this;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getNetwrokCard() {
        return this.netwrokCard;
    }

    public Deposits netwrokCard(String netwrokCard) {
        this.setNetwrokCard(netwrokCard);
        return this;
    }

    public void setNetwrokCard(String netwrokCard) {
        this.netwrokCard = netwrokCard;
    }

    public String getValueCard() {
        return this.valueCard;
    }

    public Deposits valueCard(String valueCard) {
        this.setValueCard(valueCard);
        return this;
    }

    public void setValueCard(String valueCard) {
        this.valueCard = valueCard;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public Deposits createTime(Instant createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getSeriCard() {
        return this.seriCard;
    }

    public Deposits seriCard(String seriCard) {
        this.setSeriCard(seriCard);
        return this;
    }

    public void setSeriCard(String seriCard) {
        this.seriCard = seriCard;
    }

    public String getCodeCard() {
        return this.codeCard;
    }

    public Deposits codeCard(String codeCard) {
        this.setCodeCard(codeCard);
        return this;
    }

    public void setCodeCard(String codeCard) {
        this.codeCard = codeCard;
    }

    public Long getStatus() {
        return this.status;
    }

    public Deposits status(Long status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getUserAppose() {
        return this.userAppose;
    }

    public Deposits userAppose(String userAppose) {
        this.setUserAppose(userAppose);
        return this;
    }

    public void setUserAppose(String userAppose) {
        this.userAppose = userAppose;
    }

    public String getValueChoice() {
        return this.valueChoice;
    }

    public Deposits valueChoice(String valueChoice) {
        this.setValueChoice(valueChoice);
        return this;
    }

    public void setValueChoice(String valueChoice) {
        this.valueChoice = valueChoice;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Deposits phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deposits)) {
            return false;
        }
        return getId() != null && getId().equals(((Deposits) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deposits{" +
            "id=" + getId() +
            ", articleCode='" + getArticleCode() + "'" +
            ", netwrokCard='" + getNetwrokCard() + "'" +
            ", valueCard='" + getValueCard() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", seriCard='" + getSeriCard() + "'" +
            ", codeCard='" + getCodeCard() + "'" +
            ", status=" + getStatus() +
            ", userAppose='" + getUserAppose() + "'" +
            ", valueChoice='" + getValueChoice() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
