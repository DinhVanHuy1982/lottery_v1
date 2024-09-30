package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IntroduceArticleGroup.
 */
@Entity
@Table(name = "introduce_article_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntroduceArticleGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Size(max = 50)
    @Column(name = "article_group_code", length = 50)
    private String articleGroupCode;

    @Size(max = 250)
    @Column(name = "file_id", length = 250)
    private String fileId;

    @Size(max = 500)
    @Column(name = "title_introduce", length = 500)
    private String titleIntroduce;

    @Size(max = 2000)
    @Column(name = "content_introduce", length = 2000)
    private String contentIntroduce;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IntroduceArticleGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public IntroduceArticleGroup code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getArticleGroupCode() {
        return this.articleGroupCode;
    }

    public IntroduceArticleGroup articleGroupCode(String articleGroupCode) {
        this.setArticleGroupCode(articleGroupCode);
        return this;
    }

    public void setArticleGroupCode(String articleGroupCode) {
        this.articleGroupCode = articleGroupCode;
    }

    public String getFileId() {
        return this.fileId;
    }

    public IntroduceArticleGroup fileId(String fileId) {
        this.setFileId(fileId);
        return this;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTitleIntroduce() {
        return this.titleIntroduce;
    }

    public IntroduceArticleGroup titleIntroduce(String titleIntroduce) {
        this.setTitleIntroduce(titleIntroduce);
        return this;
    }

    public void setTitleIntroduce(String titleIntroduce) {
        this.titleIntroduce = titleIntroduce;
    }

    public String getContentIntroduce() {
        return this.contentIntroduce;
    }

    public IntroduceArticleGroup contentIntroduce(String contentIntroduce) {
        this.setContentIntroduce(contentIntroduce);
        return this;
    }

    public void setContentIntroduce(String contentIntroduce) {
        this.contentIntroduce = contentIntroduce;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntroduceArticleGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((IntroduceArticleGroup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntroduceArticleGroup{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", articleGroupCode='" + getArticleGroupCode() + "'" +
            ", fileId='" + getFileId() + "'" +
            ", titleIntroduce='" + getTitleIntroduce() + "'" +
            ", contentIntroduce='" + getContentIntroduce() + "'" +
            "}";
    }
}
