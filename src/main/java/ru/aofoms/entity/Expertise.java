package ru.aofoms.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "expertise")
public class Expertise extends EntityWithId {

    @ManyToOne
    @JoinColumn(name = "id_specialty", referencedColumnName = "id")
    private ExpertSpecialty specialty;
    private int count;
    private int reexp;
    private int year;

    public ExpertSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(ExpertSpecialty specialty) {
        this.specialty = specialty;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getReexp() {
        return reexp;
    }

    public void setReexp(int reexp) {
        this.reexp = reexp;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return specialty + " = " + count + " за " + year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expertise expertise = (Expertise) o;
        return year == expertise.year &&
                Objects.equals(specialty, expertise.specialty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialty, year);
    }
}
