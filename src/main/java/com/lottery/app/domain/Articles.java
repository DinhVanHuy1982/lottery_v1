package com.lottery.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Articles.
 */
@Entity
@Table(name = "articles")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Articles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(name = "article_group_code", length = 50, nullable = false)
    private String articleGroupCode;

    @Size(max = 500)
    @Column(name = "name", length = 500)
    private String name;

    @Size(max = 500)
    @Column(name = "title", length = 500)
    private String title;

    @Size(max = 2000)
    @Column(name = "content", length = 2000)
    private String content;

    @Size(max = 250)
    @Column(name = "file_id", length = 250)
    private String fileId;

    @Size(max = 50)
    @Column(name = "update_name", length = 50)
    private String updateName;

    @Column(name = "number_choice")
    private Long numberChoice;

    @Column(name = "number_of_digits")
    private Long numberOfDigits;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "time_end")
    private String timeEnd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Articles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Articles code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public Articles title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public Articles content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileId() {
        return this.fileId;
    }

    public Articles fileId(String fileId) {
        this.setFileId(fileId);
        return this;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUpdateName() {
        return this.updateName;
    }

    public Articles updateName(String updateName) {
        this.setUpdateName(updateName);
        return this;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Long getNumberChoice() {
        return this.numberChoice;
    }

    public Articles numberChoice(Long numberChoice) {
        this.setNumberChoice(numberChoice);
        return this;
    }

    public void setNumberChoice(Long numberChoice) {
        this.numberChoice = numberChoice;
    }

    public Long getNumberOfDigits() {
        return this.numberOfDigits;
    }

    public Articles numberOfDigits(Long numberOfDigits) {
        this.setNumberOfDigits(numberOfDigits);
        return this;
    }

    public void setNumberOfDigits(Long numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    public String getTimeStart() {
        return this.timeStart;
    }

    public Articles timeStart(String timeStart) {
        this.setTimeStart(timeStart);
        return this;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return this.timeEnd;
    }

    public Articles timeEnd(String timeEnd) {
        this.setTimeEnd(timeEnd);
        return this;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Articles)) {
            return false;
        }
        return getId() != null && getId().equals(((Articles) o).getId());
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
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Articles{" +
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
