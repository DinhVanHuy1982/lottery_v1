package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.IntroduceArticle} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntroduceArticleDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String code;

    @Size(max = 50)
    private String articleCode;

    @Size(max = 500)
    private String title;

    @Size(max = 2000)
    private String content;

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

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntroduceArticleDTO)) {
            return false;
        }

        IntroduceArticleDTO introduceArticleDTO = (IntroduceArticleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, introduceArticleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntroduceArticleDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", articleCode='" + getArticleCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", fileId='" + getFileId() + "'" +
            "}";
    }
}
