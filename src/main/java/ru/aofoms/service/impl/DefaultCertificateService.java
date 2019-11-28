package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.Certificate;
import ru.aofoms.repository.CertificateRepo;
import ru.aofoms.service.CertificateService;

import java.util.List;

@Service
public class DefaultCertificateService implements CertificateService {

    private final CertificateRepo certificateRepo;


    public DefaultCertificateService(CertificateRepo certificateRepo) {
        this.certificateRepo = certificateRepo;
    }

    @Override
    public List<Certificate> findBySpecialty(long expertSpecId) {
        return certificateRepo.findBySpecialty(expertSpecId);
    }

    @Override
    public void save(Certificate certificate) {
        certificateRepo.save(certificate);
    }

    @Override
    public void delete(Certificate certificate) {
        certificateRepo.delete(certificate);
    }

}
