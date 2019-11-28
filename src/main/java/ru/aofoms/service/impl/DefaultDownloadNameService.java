package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.DownloadName;
import ru.aofoms.repository.DownloadNameRepo;
import ru.aofoms.service.DownloadNameService;

import java.time.LocalDate;

@Service
public class DefaultDownloadNameService implements DownloadNameService {

    private final DownloadNameRepo downloadNamerepo;

    public DefaultDownloadNameService(DownloadNameRepo downloadNamerepo) {
        this.downloadNamerepo = downloadNamerepo;
    }

    @Override
    public int getMaxNumber(String fileType) {
        Integer result = downloadNamerepo.getMaxNumber(fileType);

        if (result == null) result = 0;

        return result;
    }

    @Override
    public void addDoc(String fileType, int nextNumber, LocalDate now) {
        DownloadName dName = new DownloadName();
        dName.setFileType(fileType);
        dName.setFileNumber(nextNumber);
        dName.setDate(now);

        downloadNamerepo.save(dName);
    }
}
