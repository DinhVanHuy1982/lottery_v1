package com.lottery.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lottery.app.domain.FileSaves} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileSavesDTO implements Serializable {

    private Long id;

    @Size(max = 250)
    private String fileId;

    @Size(max = 500)
    private String fileName;

    @Size(max = 1000)
    private String filePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileSavesDTO)) {
            return false;
        }

        FileSavesDTO fileSavesDTO = (FileSavesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileSavesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileSavesDTO{" +
            "id=" + getId() +
            ", fileId='" + getFileId() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", filePath='" + getFilePath() + "'" +
            "}";
    }
}
