package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.IntroduceArticleGroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntroduceArticleGroupDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String code;

    @Size(max = 50)
    private String articleGroupCode;

    @Size(max = 250)
    private String fileId;

    @Size(max = 500)
    private String titleIntroduce;

    @Size(max = 2000)
    private String contentIntroduce;

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

    public String getArticleGroupCode() {
        return articleGroupCode;
    }

    public void setArticleGroupCode(String articleGroupCode) {
        this.articleGroupCode = articleGroupCode;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTitleIntroduce() {
        return titleIntroduce;
    }

    public void setTitleIntroduce(String titleIntroduce) {
        this.titleIntroduce = titleIntroduce;
    }

    public String getContentIntroduce() {
        return contentIntroduce;
    }

    public void setContentIntroduce(String contentIntroduce) {
        this.contentIntroduce = contentIntroduce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntroduceArticleGroupDTO)) {
            return false;
        }

        IntroduceArticleGroupDTO introduceArticleGroupDTO = (IntroduceArticleGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, introduceArticleGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntroduceArticleGroupDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", articleGroupCode='" + getArticleGroupCode() + "'" +
            ", fileId='" + getFileId() + "'" +
            ", titleIntroduce='" + getTitleIntroduce() + "'" +
            ", contentIntroduce='" + getContentIntroduce() + "'" +
            "}";
    }
}
