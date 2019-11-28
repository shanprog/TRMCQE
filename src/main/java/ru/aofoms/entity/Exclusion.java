package ru.aofoms.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "exclusions")
public class Exclusion extends EntityWithExpert {

    @OneToOne
    @JoinColumn(name = "id_inclusion", referencedColumnName = "id")
    private Inclusion inclusion;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reason", referencedColumnName = "id")
    private ExclusionReason exclusionReason;

    public Inclusion getInclusion() {
        return inclusion;
    }

    public void setInclusion(Inclusion inclusion) {
        this.inclusion = inclusion;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ExclusionReason getExclusionReason() {
        return exclusionReason;
    }

    public void setExclusionReason(ExclusionReason exclusionReason) {
        this.exclusionReason = exclusionReason;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exclusion exclusion = (Exclusion) o;
        return Objects.equals(inclusion, exclusion.inclusion) &&
                Objects.equals(date, exclusion.date) &&
                Objects.equals(exclusionReason, exclusion.exclusionReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inclusion, date, exclusionReason);
    }
}
