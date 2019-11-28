package ru.aofoms.service;

import ru.aofoms.entity.CodeGVS;
import ru.aofoms.entity.ExpertSpecialty;
import ru.aofoms.entity.Specialty;

import java.util.List;

public interface ExpertSpecialtyService {

    List<ExpertSpecialty> findByExpertId(long expertId);

    void save(ExpertSpecialty expertSpecialty);

    void delete(ExpertSpecialty expertSpecialty);

    List<Specialty> findAllSpecialties();

    List<CodeGVS> findAllCodeGVS();
}
