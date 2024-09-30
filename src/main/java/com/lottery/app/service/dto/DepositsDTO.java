package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.Deposits} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepositsDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String articleCode;

    @Size(max = 50)
    private String netwrokCard;

    @Size(max = 50)
    private String valueCard;

    private Instant createTime;

    @Size(max = 50)
    private String seriCard;

    @Size(max = 50)
    private String codeCard;

    private Long status;

    @Size(max = 50)
    private String userAppose;

    @Size(max = 100)
    private String valueChoice;

    @Size(max = 50)
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getNetwrokCard() {
        return netwrokCard;
    }

    public void setNetwrokCard(String netwrokCard) {
        this.netwrokCard = netwrokCard;
    }

    public String getValueCard() {
        return valueCard;
    }

    public void setValueCard(String valueCard) {
        this.valueCard = valueCard;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getSeriCard() {
        return seriCard;
    }

    public void setSeriCard(String seriCard) {
        this.seriCard = seriCard;
    }

    public String getCodeCard() {
        return codeCard;
    }

    public void setCodeCard(String codeCard) {
        this.codeCard = codeCard;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getUserAppose() {
        return userAppose;
    }

    public void setUserAppose(String userAppose) {
        this.userAppose = userAppose;
    }

    public String getValueChoice() {
        return valueChoice;
    }

    public void setValueChoice(String valueChoice) {
        this.valueChoice = valueChoice;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepositsDTO)) {
            return false;
        }

        DepositsDTO depositsDTO = (DepositsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, depositsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepositsDTO{" +
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
