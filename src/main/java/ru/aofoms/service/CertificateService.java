package ru.aofoms.service;

import ru.aofoms.entity.Certificate;
import ru.aofoms.entity.CodeGVS;

import java.util.List;

public interface CertificateService {

    List<Certificate> findBySpecialty(long expertSpecId);

    void save(Certificate certificate);

    void delete(Certificate certificate);

}
