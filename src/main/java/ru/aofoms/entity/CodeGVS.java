package ru.aofoms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "codegvs")
public class CodeGVS extends EntityWithId {

    private int number;
    @Column(length = 100)
    private String name;

    @OneToMany(mappedBy = "codeGVS")
    private List<ExpertSpecialty> certificates;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExpertSpecialty> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<ExpertSpecialty> certificates) {
        this.certificates = certificates;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeGVS codeGVS = (CodeGVS) o;
        return number == codeGVS.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
