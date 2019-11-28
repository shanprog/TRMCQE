package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.CodeGVS;
import ru.aofoms.entity.ExpertSpecialty;
import ru.aofoms.entity.Specialty;
import ru.aofoms.repository.CodeGVSRepo;
import ru.aofoms.repository.ExpertSpecialtyRepo;
import ru.aofoms.repository.SpecialtyRepo;
import ru.aofoms.service.ExpertSpecialtyService;

import java.util.List;

@Service
public class DefaultExpertSpecialtyService implements ExpertSpecialtyService {

    private final ExpertSpecialtyRepo expertSpecialtyRepo;
    private final SpecialtyRepo specialtyRepo;
    private final CodeGVSRepo codeGVSRepo;

    public DefaultExpertSpecialtyService(ExpertSpecialtyRepo expertSpecialtyRepo, SpecialtyRepo specialtyRepo, CodeGVSRepo codeGVSRepo) {
        this.expertSpecialtyRepo = expertSpecialtyRepo;
        this.specialtyRepo = specialtyRepo;
        this.codeGVSRepo = codeGVSRepo;
    }

    @Override
    public List<ExpertSpecialty> findByExpertId(long expertId) {
        return expertSpecialtyRepo.findByExpertId(expertId);
    }

    @Override
    public void save(ExpertSpecialty expertSpecialty) {
        expertSpecialtyRepo.save(expertSpecialty);
    }

    @Override
    public void delete(ExpertSpecialty expertSpecialty) {
        expertSpecialtyRepo.delete(expertSpecialty);
    }

    @Override
    public List<Specialty> findAllSpecialties() {
        return specialtyRepo.findAll();
    }

    @Override
    public List<CodeGVS> findAllCodeGVS() {
        return codeGVSRepo.findAll();
    }
}
