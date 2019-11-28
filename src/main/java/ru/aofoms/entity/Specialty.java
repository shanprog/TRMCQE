package ru.aofoms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "specialties_v015")
public class Specialty extends EntityWithId {

    private int code;
    @Column(length = 254)
    private String name;
    private int ref;
    private int okso;

    @OneToMany(mappedBy = "specialty")
    private List<ExpertSpecialty> expertSpecialty;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public int getOkso() {
        return okso;
    }

    public void setOkso(int okso) {
        this.okso = okso;
    }

    public List<ExpertSpecialty> getExpertSpecialty() {
        return expertSpecialty;
    }

    public void setExpertSpecialty(List<ExpertSpecialty> expertSpecialty) {
        this.expertSpecialty = expertSpecialty;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        return code == specialty.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
