package ru.aofoms.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "invite_orgs")
public class InviteOrg extends EntityWithId {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_org_code", referencedColumnName = "id")
    private InviteOrgCode inviteOrgCode;

    private String name;
    private String address;

    @OneToMany(mappedBy = "inviteOrg")
    private List<Inclusion> inclusions;

    public InviteOrgCode getInviteOrgCode() {
        return inviteOrgCode;
    }

    public void setInviteOrgCode(InviteOrgCode inviteOrgCode) {
        this.inviteOrgCode = inviteOrgCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Inclusion> getInclusions() {
        return inclusions;
    }

    public void setInclusions(List<Inclusion> inclusions) {
        this.inclusions = inclusions;
    }

    @Override
    public String toString() {
        return inviteOrgCode.toString() + " " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InviteOrg inviteOrg = (InviteOrg) o;
        return Objects.equals(inviteOrgCode, inviteOrg.inviteOrgCode) &&
                Objects.equals(name, inviteOrg.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inviteOrgCode, name);
    }
}
