package ru.aofoms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "diplomas")
public class Diploma extends EntityWithExpert {

    @Column(length = 128)
    private String special;

    @Column(length = 10)
    private String series;

    @Column(length = 30)
    private String number;

    @Column(name="out_org",length = 254)
    private String outOrg;

    @Column(name = "out_date")
    private LocalDate outDate;

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
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

    public String getOutOrg() {
        return outOrg;
    }

    public void setOutOrg(String outOrg) {
        this.outOrg = outOrg;
    }

    public LocalDate getOutDate() {
        return outDate;
    }

    public void setOutDate(LocalDate outDate) {
        this.outDate = outDate;
    }

    @Override
    public String toString() {
        return special + " от " + outDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diploma diploma = (Diploma) o;
        return Objects.equals(series, diploma.series) &&
                Objects.equals(number, diploma.number) &&
                Objects.equals(outDate, diploma.outDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(series, number, outDate);
    }
}
