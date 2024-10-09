package com.lottery.app.service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.ArticleGroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleGroupDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String code;

    @Size(max = 500)
    private String title;

    @Size(max = 2000)
    private String mainContent;

    @Size(max = 500)
    private String name;

    private Instant createTime;

    private Instant updateTime;

    @Size(max = 150)
    private String createName;

    @Size(max = 150)
    private String updateName;

    @Size(max = 500)
    private String fileName;

    @Size(max = 1000)
    private String filePath;

    @Size(max = 250)
    private String fileId;

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

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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
        if (!(o instanceof ArticleGroupDTO)) {
            return false;
        }

        ArticleGroupDTO articleGroupDTO = (ArticleGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, articleGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleGroupDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", mainContent='" + getMainContent() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", createName='" + getCreateName() + "'" +
            ", updateName='" + getUpdateName() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", fileId='" + getFileId() + "'" +
            "}";
    }
}
