package ru.aofoms.service;

import javafx.collections.ObservableList;
import ru.aofoms.entity.*;
import ru.aofoms.util.ExpertDocType;
import ru.aofoms.util.SaveInfo;
import ru.aofoms.util.TableExpert;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Collection;
import java.util.List;

public interface ExpertService {
    Collection<? extends TableExpert> getExpertForMain();

    Expert findById(long id);

    ObservableList<EntityWithId> getElements(long expertId, ExpertDocType expertDocType);

    void updateExpert(List<SaveInfo> saveInfoList);

    String findSnils(String snils);

    String findNumber(String number);

    void save(Expert expert);

    void saveCertificates(List<Certificate> listByType);

    void saveExpertise(List<Expertise> listByType);

    List<Expert> findAllForXLS();

    List<Expert> findAll();

    LocalDate getLastExclusionDate(long expertId);

    LocalDate getLastInclusionDate(long expertId);

    QualificationDoc getLastQualificationDoc(long expertSpecId);

    AcademicDiploma getMaxAcademicDiploma(List<AcademicDiploma> academicDiplomas);

    List<Expert> getListActiveExperts();

    List<Expert> getListNonActiveExperts();

    List<Expert> findAllForXML();

    Passport findLastPassport(long expertId);

    Inclusion findLastInclusion(long expertId);

    List<Diploma> findDiplomasByExpertId(long expertId);

    List<Certificate> findActiveCertificates();

    List<TrainingDoc> findActiveTrainingDocs(long expertId);

    List<AcademicDiploma> findAcademicDiplomasByExpertId(long expertId);

    List<Workplace> findWorkplacesByExpertId(long expertId);

    List<QualificationDoc> findQualificationDocsByExpertId(long expertId);

    List<ExpertSpecialty> findExpertSpecialtiesByExpertId(long expertId);

    List<Certificate> findCertificatesBySpecId(long specId);

    void updateEditDate(Expert expert);

    String getNextNumber();

    String getLastExclusion(long id);
}
