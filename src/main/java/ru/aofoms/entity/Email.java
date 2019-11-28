package ru.aofoms.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "emails")
public class Email extends EntityWithExpert {

    @Column(length = 45)
    private String email;

    @Column(name = "sort_order")
    private int sortOrder;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return email.equals(email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
