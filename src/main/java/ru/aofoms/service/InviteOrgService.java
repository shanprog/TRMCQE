package ru.aofoms.service;

import ru.aofoms.entity.InviteOrg;
import ru.aofoms.entity.InviteOrgCode;

import java.util.List;

public interface InviteOrgService {

    List<InviteOrgCode> findAllInviteOrgCode();

    List<InviteOrg> findAll();

    void save(InviteOrg object);

    void delete(InviteOrg object);

    InviteOrg findById(long id);
}
