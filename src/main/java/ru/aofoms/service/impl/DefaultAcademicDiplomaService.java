package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.AcademicDegree;
import ru.aofoms.entity.AcademicDiploma;
import ru.aofoms.repository.AcademicDegreeRepo;
import ru.aofoms.repository.AcademicDiplomaRepo;
import ru.aofoms.service.AcademicDiplomaService;

import java.util.List;

@Service
public class DefaultAcademicDiplomaService implements AcademicDiplomaService {

    private final AcademicDegreeRepo degreeRepo;
    private final AcademicDiplomaRepo diplomaRepo;

    public DefaultAcademicDiplomaService(AcademicDegreeRepo degreeRepo, AcademicDiplomaRepo diplomaRepo) {
        this.degreeRepo = degreeRepo;
        this.diplomaRepo = diplomaRepo;
    }

    @Override
    public List<AcademicDiploma> findByExpertId(long expertId) {
        return diplomaRepo.findByExpertId(expertId);
    }

    @Override
    public void save(AcademicDiploma academicDiploma) {
        diplomaRepo.save(academicDiploma);
    }

    @Override
    public void delete(AcademicDiploma academicDiploma) {
        diplomaRepo.delete(academicDiploma);
    }

    @Override
    public List<AcademicDegree> getAcademicDegrees() {
        return degreeRepo.findAll();
    }
}
