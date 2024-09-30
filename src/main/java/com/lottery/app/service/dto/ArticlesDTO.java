package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.Articles} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticlesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String code;

    @Size(max = 500)
    private String title;

    @Size(max = 2000)
    private String content;

    @Size(max = 250)
    private String fileId;

    @Size(max = 50)
    private String updateName;

    private Long numberChoice;

    private Long numberOfDigits;

    private String timeStart;

    private String timeEnd;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Long getNumberChoice() {
        return numberChoice;
    }

    public void setNumberChoice(Long numberChoice) {
        this.numberChoice = numberChoice;
    }

    public Long getNumberOfDigits() {
        return numberOfDigits;
    }

    public void setNumberOfDigits(Long numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticlesDTO)) {
            return false;
        }

        ArticlesDTO articlesDTO = (ArticlesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, articlesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticlesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", fileId='" + getFileId() + "'" +
            ", updateName='" + getUpdateName() + "'" +
            ", numberChoice=" + getNumberChoice() +
            ", numberOfDigits=" + getNumberOfDigits() +
            ", timeStart='" + getTimeStart() + "'" +
            ", timeEnd='" + getTimeEnd() + "'" +
            "}";
    }
}