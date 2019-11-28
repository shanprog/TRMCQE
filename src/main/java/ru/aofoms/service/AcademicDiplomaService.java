package ru.aofoms.service;

import ru.aofoms.entity.AcademicDegree;
import ru.aofoms.entity.AcademicDiploma;

import java.util.List;

public interface AcademicDiplomaService {

    List<AcademicDiploma> findByExpertId(long expertId);

    void save(AcademicDiploma academicDiploma);

    void delete(AcademicDiploma academicDiploma);

    List<AcademicDegree> getAcademicDegrees();
}
