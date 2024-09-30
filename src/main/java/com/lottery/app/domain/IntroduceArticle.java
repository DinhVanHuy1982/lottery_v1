package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IntroduceArticle.
 */
@Entity
@Table(name = "introduce_article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntroduceArticle implements Serializable {

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

    @Size(max = 500)
    @Column(name = "title", length = 500)
    private String title;

    @Size(max = 2000)
    @Column(name = "content", length = 2000)
    private String content;

    @Size(max = 250)
    @Column(name = "file_id", length = 250)
    private String fileId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IntroduceArticle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public IntroduceArticle code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getArticleCode() {
        return this.articleCode;
    }

    public IntroduceArticle articleCode(String articleCode) {
        this.setArticleCode(articleCode);
        return this;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getTitle() {
        return this.title;
    }

    public IntroduceArticle title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public IntroduceArticle content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileId() {
        return this.fileId;
    }

    public IntroduceArticle fileId(String fileId) {
        this.setFileId(fileId);
        return this;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntroduceArticle)) {
            return false;
        }
        return getId() != null && getId().equals(((IntroduceArticle) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntroduceArticle{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", articleCode='" + getArticleCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", fileId='" + getFileId() + "'" +
            "}";
    }
}
