package ru.aofoms.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntityWithExpert extends EntityWithId {

    @ManyToOne
    @JoinColumn(name = "id_expert", referencedColumnName = "id")
    protected Expert expert;

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    @Override
    public String toString() {
        return "EntityWithExpert{" +
                "expert=" + expert +
                '}';
    }
}
