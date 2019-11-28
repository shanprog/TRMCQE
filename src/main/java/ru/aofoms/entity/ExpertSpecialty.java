package ru.aofoms.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "specialties")
public class ExpertSpecialty extends EntityWithExpert {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_specialty", referencedColumnName = "id")
    private Specialty specialty;

    @ManyToOne
    @JoinColumn(name = "id_workplace", referencedColumnName = "id")
    private Workplace workplace;

    @OneToMany(mappedBy = "specialty")
    private List<Certificate> certificates;

    @OneToMany(mappedBy = "specialty")
    private List<Expertise> expertise;

    @OneToMany(mappedBy = "specialty")
    private List<QualificationDoc> qualificationDocs;

    @ManyToOne
    @JoinColumn(name = "id_codegvs", referencedColumnName = "id")
    private CodeGVS codeGVS;

    private int experience;

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public List<Expertise> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<Expertise> expertise) {
        this.expertise = expertise;
    }

    public List<QualificationDoc> getQualificationDocs() {
        return qualificationDocs;
    }

    public void setQualificationDocs(List<QualificationDoc> qualificationDocs) {
        this.qualificationDocs = qualificationDocs;
    }

    public CodeGVS getCodeGVS() {
        return codeGVS;
    }

    public void setCodeGVS(CodeGVS codeGVS) {
        this.codeGVS = codeGVS;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return specialty.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialty);
    }
}
