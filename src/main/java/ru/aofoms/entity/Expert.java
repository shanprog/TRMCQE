package ru.aofoms.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "experts")
public class Expert extends EntityWithId {

    @Column(length = 7)
    private String number;
    @Column(length = 40)
    private String surname;
    @Column(length = 40)
    private String name;
    @Column(length = 40)
    private String patronymic;
    @Column(length = 14)
    private String snils;
    @Column(name = "edit_date")
    private LocalDate editDate;

    @OneToMany(mappedBy = "expert")
    private List<Phone> phones;

    @OneToMany(mappedBy = "expert")
    private List<Email> emails;

    @OneToMany(mappedBy = "expert")
    private List<Passport> passports;

    @OneToMany(mappedBy = "expert")
    private List<Diploma> diplomas;

    @OneToMany(mappedBy = "expert")
    private List<TrainingDoc> trainingDocs;

    @OneToMany(mappedBy = "expert")
    private List<AcademicDiploma> academicDiplomas;

    @OneToMany(mappedBy = "expert")
    private List<Workplace> workplaces;

    @OneToMany(mappedBy = "expert")
    private List<QualificationDoc> qualificationDocs;

    @OneToMany(mappedBy = "expert")
    private List<Inclusion> inclusions;

    @OneToMany(mappedBy = "expert")
    private List<Exclusion> exclusions;

    @OneToMany(mappedBy = "expert")
    private List<ExpertSpecialty> expertSpecialties;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public LocalDate getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDate editDate) {
        this.editDate = editDate;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<Passport> getPassports() {
        return passports;
    }

    public void setPassports(List<Passport> passports) {
        this.passports = passports;
    }

    public List<Diploma> getDiplomas() {
        return diplomas;
    }

    public void setDiplomas(List<Diploma> diplomas) {
        this.diplomas = diplomas;
    }

    public List<TrainingDoc> getTrainingDocs() {
        return trainingDocs;
    }

    public void setTrainingDocs(List<TrainingDoc> trainingDocs) {
        this.trainingDocs = trainingDocs;
    }

    public List<AcademicDiploma> getAcademicDiplomas() {
        return academicDiplomas;
    }

    public void setAcademicDiplomas(List<AcademicDiploma> academicDiplomas) {
        this.academicDiplomas = academicDiplomas;
    }

    public List<Workplace> getWorkplaces() {
        return workplaces;
    }

    public void setWorkplaces(List<Workplace> workplaces) {
        this.workplaces = workplaces;
    }

    public List<QualificationDoc> getQualificationDocs() {
        return qualificationDocs;
    }

    public void setQualificationDocs(List<QualificationDoc> qualificationDocs) {
        this.qualificationDocs = qualificationDocs;
    }

    public List<Inclusion> getInclusions() {
        return inclusions;
    }

    public void setInclusions(List<Inclusion> inclusions) {
        this.inclusions = inclusions;
    }

    public List<Exclusion> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<Exclusion> exclusions) {
        this.exclusions = exclusions;
    }

    public List<ExpertSpecialty> getExpertSpecialties() {
        return expertSpecialties;
    }

    public void setExpertSpecialties(List<ExpertSpecialty> expertSpecialties) {
        this.expertSpecialties = expertSpecialties;
    }

    @Override
    public String toString() {
        return number + " " + surname + " " + name + " " + patronymic;
    }
}
