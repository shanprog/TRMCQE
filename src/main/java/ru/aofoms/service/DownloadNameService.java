package ru.aofoms.service;

import java.time.LocalDate;

public interface DownloadNameService {
    int getMaxNumber(String fileType);

    void addDoc(String fileType, int nextNumber, LocalDate now);
}
