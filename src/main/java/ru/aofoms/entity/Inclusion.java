package ru.aofoms.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "inclusions")
public class Inclusion extends EntityWithExpert {

    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_invite_org", referencedColumnName = "id")
    private InviteOrg inviteOrg;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public InviteOrg getInviteOrg() {
        return inviteOrg;
    }

    public void setInviteOrg(InviteOrg inviteOrg) {
        this.inviteOrg = inviteOrg;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inclusion inclusion = (Inclusion) o;
        return Objects.equals(date, inclusion.date) &&
                Objects.equals(inviteOrg, inclusion.inviteOrg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, inviteOrg);
    }
}
