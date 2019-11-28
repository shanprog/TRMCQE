package ru.aofoms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "workplaces")
public class Workplace extends EntityWithExpert {

    @Column(length = 254)
    private String name;
    @Column(length = 254)
    private String position;
    @Column(length = 254)
    private String address;

    @OneToMany(mappedBy = "workplace")
    private List<ExpertSpecialty> expertSpecialties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ExpertSpecialty> getExpertSpecialties() {
        return expertSpecialties;
    }

    public void setExpertSpecialties(List<ExpertSpecialty> expertSpecialties) {
        this.expertSpecialties = expertSpecialties;
    }

    @Override
    public String toString() {
        return name + ": " + position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workplace workplace = (Workplace) o;
        return Objects.equals(name, workplace.name) &&
                Objects.equals(position, workplace.position) &&
                Objects.equals(address, workplace.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position, address);
    }
}
