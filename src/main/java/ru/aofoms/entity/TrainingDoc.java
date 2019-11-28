package ru.aofoms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "training_docs")
public class TrainingDoc extends EntityWithExpert {

    @Column(name = "cycle_name", length = 254)
    private String cycleName;

    private int hours;

    @Column(name = "out_org", length = 254)
    private String outOrg;

    @Column(name = "out_date")
    private LocalDate outDate;

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingDoc that = (TrainingDoc) o;
        return hours == that.hours &&
                Objects.equals(cycleName, that.cycleName) &&
                Objects.equals(outDate, that.outDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cycleName, hours, outDate);
    }

    @Override
    public String toString() {
        return "Документ от " + outDate;
    }
}
