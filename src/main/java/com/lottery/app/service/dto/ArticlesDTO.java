package com.lottery.app.service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
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

    @Size(max = 50)
    private String articleGroupCode;

    @Size(max = 500)
    private String name;

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

    private List<LevelDepositsDTO> levelDeposits;
    private List<IntroduceArticleDTO> lstIntroduce;
    private Long numberPrize;
    private String action;
    private Long totalLevelDeposits;
    private Long totalIntroduceArticle;
    private String articleGroupName;

    public String getArticleGroupName() {
        return articleGroupName;
    }

    public void setArticleGroupName(String articleGroupName) {
        this.articleGroupName = articleGroupName;
    }

    public Long getTotalLevelDeposits() {
        return totalLevelDeposits;
    }

    public void setTotalLevelDeposits(Long totalLevelDeposits) {
        this.totalLevelDeposits = totalLevelDeposits;
    }

    public Long getTotalIntroduceArticle() {
        return totalIntroduceArticle;
    }

    public void setTotalIntroduceArticle(Long totalIntroduceArticle) {
        this.totalIntroduceArticle = totalIntroduceArticle;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getNumberPrize() {
        return numberPrize;
    }

    public void setNumberPrize(Long numberPrize) {
        this.numberPrize = numberPrize;
    }

    public List<IntroduceArticleDTO> getLstIntroduce() {
        return lstIntroduce;
    }

    public void setLstIntroduce(List<IntroduceArticleDTO> lstIntroduce) {
        this.lstIntroduce = lstIntroduce;
    }

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

    public String getArticleGroupCode() {
        return articleGroupCode;
    }

    public void setArticleGroupCode(String articleGroupCode) {
        this.articleGroupCode = articleGroupCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<LevelDepositsDTO> getLevelDeposits() {
        return levelDeposits;
    }

    public void setLevelDeposits(List<LevelDepositsDTO> levelDeposits) {
        this.levelDeposits = levelDeposits;
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
