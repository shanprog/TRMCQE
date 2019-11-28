package ru.aofoms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "download_names")

public class DownloadName extends EntityWithId {

    @Column(name = "file_type", length = 10)
    private String fileType;

    @Column(name = "file_number")
    private int fileNumber;

    @Column(name = "download_date")
    private LocalDate date;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
