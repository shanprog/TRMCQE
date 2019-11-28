package ru.aofoms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "invite_org_codes_r009")
public class InviteOrgCode extends EntityWithId {

    private int code;
    @Column(length = 254)
    private String name;

    @OneToMany(mappedBy = "inviteOrgCode")
    private List<InviteOrg> inviteOrgs;

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

    public List<InviteOrg> getInviteOrgs() {
        return inviteOrgs;
    }

    public void setInviteOrgs(List<InviteOrg> inviteOrgs) {
        this.inviteOrgs = inviteOrgs;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InviteOrgCode that = (InviteOrgCode) o;
        return code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
