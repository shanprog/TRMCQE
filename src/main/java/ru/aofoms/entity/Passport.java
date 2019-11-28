package ru.aofoms.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "passports")
public class Passport extends EntityWithExpert {

    @Column(length = 5)
    private String series;

    @Column(length = 15)
    private String number;

    @Column(name = "out_org")
    private String outOrg;

    @Column(name = "out_date")
    private LocalDate outDate;

    private String registration;

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

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {
        return series + " " + number + " от " + outDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return Objects.equals(series, passport.series) &&
                number.equals(passport.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(series, number);
    }
}
