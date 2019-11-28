package ru.aofoms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "academic_degree_r012")
public class AcademicDegree extends EntityWithId {

    private int code;
    @Column(length = 254)
    private String name;

    @OneToMany(mappedBy = "academicDegree")
    private List<AcademicDiploma> academicDiplomas;

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

    public List<AcademicDiploma> getAcademicDiplomas() {
        return academicDiplomas;
    }

    public void setAcademicDiplomas(List<AcademicDiploma> academicDiplomas) {
        this.academicDiplomas = academicDiplomas;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicDegree that = (AcademicDegree) o;
        return code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
