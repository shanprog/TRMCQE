package ru.aofoms.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "qualification_docs")
public class QualificationDoc extends EntityWithExpert {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_specialty", referencedColumnName = "id")
    private ExpertSpecialty specialty;

    @Column(length = 45)
    private String series;
    @Column(length = 45)
    private String number;
    @Column(name = "out_org", length = 254)
    private String outOrg;
    @Column(name = "out_date")
    private LocalDate outDate;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getOutOrg() {
        return outOrg;
    }

    public void setOutOrg(String outOrg) {
        this.outOrg = outOrg;
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

    public ExpertSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(ExpertSpecialty specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return category.toString() + " документ №" + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualificationDoc that = (QualificationDoc) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(outDate, that.outDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, outDate);
    }
}
