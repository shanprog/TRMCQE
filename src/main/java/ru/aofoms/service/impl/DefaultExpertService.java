package ru.aofoms.service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import ru.aofoms.entity.*;
import ru.aofoms.repository.ExpertRepo;
import ru.aofoms.service.*;
import ru.aofoms.util.Action;
import ru.aofoms.util.ExpertDocType;
import ru.aofoms.util.SaveInfo;
import ru.aofoms.util.TableExpert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultExpertService implements ExpertService {

    private final ExpertRepo expertRepo;

    private final PhoneService phoneService;
    private final EmailService emailService;
    private final PassportService passportService;
    private final DiplomaService diplomaService;
    private final TrainingDocService trainingDocService;
    private final AcademicDiplomaService academicDiplomaService;
    private final WorkplaceService workplaceService;
    private final QualificationDocService qualificationDocService;
    private final InclusionService inclusionService;
    private final ExclusionService exclusionService;
    private final ExpertSpecialtyService expertSpecialtyService;
    private final ExpertiseService expertiseService;
    private final CertificateService certificateService;
    private final InviteOrgService inviteOrgService;

    public DefaultExpertService(ExpertRepo expertRepo, PhoneService phoneRepo, EmailService emailService, PassportService passportService, DiplomaService diplomaService, TrainingDocService trainingDocService, AcademicDiplomaService academicDiplomaService, WorkplaceService workplaceService, QualificationDocService qualificationDocService, InclusionService inclusionService, ExclusionService exclusionService, ExpertSpecialtyService expertSpecialtyService, ExpertiseService expertiseService, CertificateService certificateService, InviteOrgService inviteOrgService) {
        this.expertRepo = expertRepo;
        this.phoneService = phoneRepo;
        this.emailService = emailService;
        this.passportService = passportService;
        this.diplomaService = diplomaService;
        this.trainingDocService = trainingDocService;
        this.academicDiplomaService = academicDiplomaService;
        this.workplaceService = workplaceService;
        this.qualificationDocService = qualificationDocService;
        this.inclusionService = inclusionService;
        this.exclusionService = exclusionService;
        this.expertSpecialtyService = expertSpecialtyService;
        this.expertiseService = expertiseService;
        this.certificateService = certificateService;
        this.inviteOrgService = inviteOrgService;
    }

    @Override
    public Collection<? extends TableExpert> getExpertForMain() {

        List<Expert> experts = expertRepo.findAll();
        List<TableExpert> result = new ArrayList<>();

        for (Expert expert : experts) {
            result.add(new TableExpert(expert, getLastExclusion(expert.getId())));
        }

        return result;
    }

    @Override
    public Expert findById(long id) {
        return expertRepo.findById(id).get();
    }

    @Override
    public ObservableList<EntityWithId> getElements(long expertId, ExpertDocType docType) {

        ObservableList<EntityWithId> resultList = FXCollections.observableArrayList();

        switch (docType) {
            case EMAIL:
                resultList.setAll(emailService.findByExpertId(expertId));
                break;
            case PHONE:
                resultList.setAll(phoneService.findByExpertId(expertId));
                break;
            case PASSPORT:
                resultList.setAll(passportService.findByExpertId(expertId));
                break;
            case DIPLOMA:
                resultList.setAll(diplomaService.findByExpertId(expertId));
                break;
            case TRAINING_DOC:
                resultList.setAll(trainingDocService.findByExpertId(expertId));
                break;
            case ACADEMIC_DIPLOMA:
                resultList.setAll(academicDiplomaService.findByExpertId(expertId));
                break;
            case WORKPLACE:
                resultList.setAll(workplaceService.findByExpertId(expertId));
                break;
            case QUALIFICATION:
                resultList.setAll(qualificationDocService.findByExpertId(expertId));
                break;
            case INCLUSION:
                resultList.setAll(inclusionService.findByExpertId(expertId));
                break;
            case EXCLUSION:
                resultList.setAll(exclusionService.findByExpertId(expertId));
                break;
            case SPECIALTY:
                resultList.setAll(expertSpecialtyService.findByExpertId(expertId));
                break;
            case EXPERTISE: {
                List<ExpertSpecialty> specialties = expertSpecialtyService.findByExpertId(expertId);

                List<Expertise> tempList = new ArrayList<>();
                specialties.stream().map(specialty -> expertiseService.findBySpecialty(specialty.getId())).forEach(tempList::addAll);

                resultList.setAll(tempList);
                break;
            }
            case CERTIFICATE: {
                List<ExpertSpecialty> specialties = expertSpecialtyService.findByExpertId(expertId);

                List<Certificate> tempList = new ArrayList<>();
                specialties.stream().map(specialty -> certificateService.findBySpecialty(specialty.getId())).forEach(tempList::addAll);

                resultList.setAll(tempList);
                break;
            }
        }

        return resultList;
    }

    @Override
    public void updateExpert(List<SaveInfo> saveInfoList) {

        saveInfoList.forEach(saveInfo -> {

            if (saveInfo.getAction() == Action.ADD || saveInfo.getAction() == Action.EDIT) {

                switch (saveInfo.getExpertDocType()) {
                    case PHONE:
                        phoneService.save((Phone) saveInfo.getObject());
                        break;
                    case EMAIL:
                        emailService.save((Email) saveInfo.getObject());
                        break;
                    case PASSPORT:
                        passportService.save((Passport) saveInfo.getObject());
                        break;
                    case DIPLOMA:
                        diplomaService.save((Diploma) saveInfo.getObject());
                        break;
                    case TRAINING_DOC:
                        trainingDocService.save((TrainingDoc) saveInfo.getObject());
                        break;
                    case ACADEMIC_DIPLOMA:
                        academicDiplomaService.save((AcademicDiploma) saveInfo.getObject());
                        break;
                    case WORKPLACE:
                        workplaceService.save((Workplace) saveInfo.getObject());
                        break;
                    case QUALIFICATION:
                        qualificationDocService.save((QualificationDoc) saveInfo.getObject());
                        break;
                    case INCLUSION:
                        inclusionService.save((Inclusion) saveInfo.getObject());
                        break;
                    case EXCLUSION:
                        exclusionService.save((Exclusion) saveInfo.getObject());
                        break;
                    case SPECIALTY:
                        expertSpecialtyService.save((ExpertSpecialty) saveInfo.getObject());
                        break;
                    case CERTIFICATE:
                        certificateService.save((Certificate) saveInfo.getObject());
                        break;
                    case EXPERTISE:
                        expertiseService.save((Expertise) saveInfo.getObject());
                        break;
                }
            }

            if (saveInfo.getAction() == Action.DELETE) {
                switch (saveInfo.getExpertDocType()) {
                    case PHONE:
                        phoneService.delete((Phone) saveInfo.getObject());
                        break;
                    case EMAIL:
                        emailService.delete((Email) saveInfo.getObject());
                        break;
                    case PASSPORT:
                        passportService.delete((Passport) saveInfo.getObject());
                        break;
                    case DIPLOMA:
                        diplomaService.delete((Diploma) saveInfo.getObject());
                        break;
                    case TRAINING_DOC:
                        trainingDocService.delete((TrainingDoc) saveInfo.getObject());
                        break;
                    case ACADEMIC_DIPLOMA:
                        academicDiplomaService.delete((AcademicDiploma) saveInfo.getObject());
                        break;
                    case WORKPLACE:
                        workplaceService.delete((Workplace) saveInfo.getObject());
                        break;
                    case QUALIFICATION:
                        qualificationDocService.delete((QualificationDoc) saveInfo.getObject());
                        break;
                    case INCLUSION:
                        inclusionService.delete((Inclusion) saveInfo.getObject());
                        break;
                    case EXCLUSION:
                        exclusionService.delete((Exclusion) saveInfo.getObject());
                        break;
                    case SPECIALTY:
                        expertSpecialtyService.delete((ExpertSpecialty) saveInfo.getObject());
                        break;
                    case CERTIFICATE:
                        certificateService.delete((Certificate) saveInfo.getObject());
                        break;
                    case EXPERTISE:
                        expertiseService.delete((Expertise) saveInfo.getObject());
                        break;
                }
            }
        });


    }

    @Override
    public String findSnils(String snils) {
        return expertRepo.findSnils(snils);
    }

    @Override
    public String findNumber(String number) {
        return expertRepo.findNumber(number);
    }

    @Override
    public void save(Expert expert) {
        expertRepo.save(expert);

        expert.getPassports().forEach(passportService::save);
        expert.getPhones().forEach(phoneService::save);
        expert.getEmails().forEach(emailService::save);
        expert.getInclusions().forEach(inclusionService::save);
        expert.getWorkplaces().forEach(workplaceService::save);
        expert.getDiplomas().forEach(diplomaService::save);
        expert.getAcademicDiplomas().forEach(academicDiplomaService::save);
        expert.getTrainingDocs().forEach(trainingDocService::save);
        expert.getExpertSpecialties().forEach(expertSpecialtyService::save);
        expert.getQualificationDocs().forEach(qualificationDocService::save);
    }

    @Override
    public void saveCertificates(List<Certificate> listByType) {
        listByType.forEach(certificateService::save);
    }

    @Override
    public void saveExpertise(List<Expertise> listByType) {
        listByType.forEach(expertiseService::save);
    }

    @Override
    public List<Expert> findAll() {
        return expertRepo.findAll();
    }

    @Override
    public List<Expert> findAllForXLS() {

        List<Expert> result = expertRepo.findAll();

        for (Expert e : result) {
            e.setWorkplaces(workplaceService.findByExpertId(e.getId()));
            e.setPhones(phoneService.findByExpertId(e.getId()));
            e.setAcademicDiplomas(academicDiplomaService.findByExpertId(e.getId()));

            List<ExpertSpecialty> expertSpecialties = expertSpecialtyService.findByExpertId(e.getId());
            expertSpecialties.forEach(es -> {
                es.setCertificates(certificateService.findBySpecialty(es.getId()));
                es.setExpertise(expertiseService.findBySpecialtyForXLS(es.getId(), LocalDate.now().getYear() - 1));
                es.setQualificationDocs(qualificationDocService.findBySpecialty(es.getId()));
            });

            e.setExpertSpecialties(expertSpecialties);

            e.setExclusions(exclusionService.findByExpertId(e.getId()));
            e.setInclusions(inclusionService.findByExpertId(e.getId()));
        }

        return result;
    }

    @Override
    public LocalDate getLastExclusionDate(long expertId) {
        return exclusionService.findLastExclusionDate(expertId);
    }

    @Override
    public LocalDate getLastInclusionDate(long expertId) {
        return inclusionService.findLastInclusionDate(expertId);
    }

    @Override
    public QualificationDoc getLastQualificationDoc(long expertSpecId) {
        return qualificationDocService.getLastQualificationDoc(expertSpecId);
    }

    @Override
    public AcademicDiploma getMaxAcademicDiploma(List<AcademicDiploma> academicDiplomas) {

        if (academicDiplomas.size() > 0) {
            AcademicDiploma result = academicDiplomas.get(0);
            for (AcademicDiploma academicDiploma : academicDiplomas) {
                if (result.getAcademicDegree().getCode() < academicDiploma.getAcademicDegree().getCode()) {
                    result = academicDiploma;
                }
            }

            return result;
        }

        return null;
    }

    @Override
    public List<Expert> getListActiveExperts() {

        List<Expert> result = new ArrayList<>();

        for (Expert e : expertRepo.findAll()) {
            if (getLastExclusionDate(e.getId()) == null || getLastInclusionDate(e.getId()).isAfter(getLastExclusionDate(e.getId()))) {
                result.add(e);
            }
        }

        return result;
    }

    @Override
    public List<Expert> getListNonActiveExperts() {
        List<Expert> result = new ArrayList<>();

        for (Expert e : expertRepo.findAll()) {
            if (getLastExclusionDate(e.getId()) != null && getLastInclusionDate(e.getId()).isBefore(getLastExclusionDate(e.getId()))) {
                result.add(e);
            }
        }

        return result;
    }

    @Override
    public List<Expert> findAllForXML() {
        List<Expert> result = expertRepo.findAll();

        for (Expert e : result) {
            e.setPhones(phoneService.findByExpertId(e.getId()));
            e.setEmails(emailService.findByExpertId(e.getId()));

            List<Inclusion> inclusions = inclusionService.findByExpertId(e.getId());
            inclusions.forEach(incl -> incl.setInviteOrg(inviteOrgService.findById(incl.getInviteOrg().getId())));

            e.setInclusions(inclusions);
            e.setAcademicDiplomas(academicDiplomaService.findByExpertId(e.getId()));
            e.setExclusions(exclusionService.findByExpertId(e.getId()));

            List<ExpertSpecialty> expertSpecialties = expertSpecialtyService.findByExpertId(e.getId());
            expertSpecialties.forEach(es -> {
                es.setCertificates(certificateService.findBySpecialty(es.getId()));
                es.setQualificationDocs(qualificationDocService.findBySpecialty(es.getId()));
                es.setExpertise(expertiseService.findBySpecialtyForXLS(es.getId(), LocalDate.now().getYear() - 1));
                es.setWorkplace(workplaceService.findById(es.getWorkplace().getId()));
            });

            e.setExpertSpecialties(expertSpecialties);

        }

        return result;
    }

    @Override
    public Passport findLastPassport(long expertId) {
        return passportService.findLastPassport(expertId);
    }

    @Override
    public Inclusion findLastInclusion(long expertId) {
        return inclusionService.findLastInclusion(expertId);
    }

    @Override
    public List<Diploma> findDiplomasByExpertId(long expertId) {
        return diplomaService.findByExpertId(expertId);
    }

    @Override
    public List<Certificate> findActiveCertificates() {
        return null;
    }

    @Override
    public List<TrainingDoc> findActiveTrainingDocs(long expertId) {
        return trainingDocService.findByExpertId(expertId).stream().filter(td -> td.getOutDate().plusYears(5).isAfter(LocalDate.now())).collect(Collectors.toList());
    }

    @Override
    public List<AcademicDiploma> findAcademicDiplomasByExpertId(long expertId) {
        return academicDiplomaService.findByExpertId(expertId);
    }

    @Override
    public List<Workplace> findWorkplacesByExpertId(long expertId) {
        return workplaceService.findByExpertId(expertId);
    }

    @Override
    public List<QualificationDoc> findQualificationDocsByExpertId(long expertId) {
        return qualificationDocService.findByExpertId(expertId);
    }

    @Override
    public List<ExpertSpecialty> findExpertSpecialtiesByExpertId(long expertId) {
        return expertSpecialtyService.findByExpertId(expertId);
    }

    @Override
    public List<Certificate> findCertificatesBySpecId(long specId) {
        return certificateService.findBySpecialty(specId);
    }

    @Override
    public void updateEditDate(Expert expert) {
        expert.setEditDate(LocalDate.now());
        expertRepo.save(expert);
    }

    @Override
    public String getNextNumber() {
        String number = expertRepo.findMaxNumber();
        if (number == null) return "2800001";
        number = String.valueOf(Integer.valueOf(number) + 1);
        return number;
    }

    @Override
    public String getLastExclusion(long id) {
        Exclusion maxExclusion = exclusionService.findLastExclusion(id);
        Inclusion maxInclusion = inclusionService.findLastInclusion(id);

        if (maxExclusion != null) {
            return maxExclusion.getDate().isAfter(maxInclusion.getDate()) ? maxExclusion.getDate().toString() : "";
        } else
            return "";
    }
}
