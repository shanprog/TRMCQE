package ru.aofoms.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "academic_diplomas")
public class AcademicDiploma extends EntityWithExpert {

    @Column(length = 45)
    private String series;

    @Column(length = 45)
    private String number;

    @Column(name = "out_date")
    private LocalDate outDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_degree", referencedColumnName = "id")
    private AcademicDegree academicDegree;

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

    public AcademicDegree getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(AcademicDegree academicDegree) {
        this.academicDegree = academicDegree;
    }

    @Override
    public String toString() {
        return academicDegree.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicDiploma that = (AcademicDiploma) o;
        return Objects.equals(series, that.series) &&
                Objects.equals(number, that.number) &&
                Objects.equals(outDate, that.outDate) &&
                Objects.equals(academicDegree, that.academicDegree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(series, number, outDate, academicDegree);
    }
}
