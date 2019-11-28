package ru.aofoms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "categories_r011")
public class Category extends EntityWithId {

    private int code;
    @Column(length = 254)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<QualificationDoc> qualificationDocs;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QualificationDoc> getQualificationDocs() {
        return qualificationDocs;
    }

    public void setQualificationDocs(List<QualificationDoc> qualificationDocs) {
        this.qualificationDocs = qualificationDocs;
    }

    @Override
    public String toString() {
        return name;
    }
}
