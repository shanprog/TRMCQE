package ru.aofoms.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "certificates")
public class Certificate extends EntityWithId {

    @ManyToOne
    @JoinColumn(name = "id_specialty", referencedColumnName = "id")
    private ExpertSpecialty specialty;

    @Column(length = 45)
    private String series;

    @Column(length = 45)
    private String number;

    @Column(name = "out_date")
    private LocalDate outDate;

    @Column(name = "out_org", length = 254)
    private String outOrg;


    public ExpertSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(ExpertSpecialty specialty) {
        this.specialty = specialty;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getOutDate() {
        return outDate;
    }

    public void setOutDate(LocalDate outDate) {
        this.outDate = outDate;
    }

    public String getOutOrg() {
        return outOrg;
    }

    public void setOutOrg(String outOrg) {
        this.outOrg = outOrg;
    }

    @Override
    public String toString() {
        return specialty + " от " + outDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return Objects.equals(specialty, that.specialty) &&
                Objects.equals(outDate, that.outDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialty, outDate);
    }
}
