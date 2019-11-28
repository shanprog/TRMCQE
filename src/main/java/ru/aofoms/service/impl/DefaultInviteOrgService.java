package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.InviteOrg;
import ru.aofoms.entity.InviteOrgCode;
import ru.aofoms.repository.InviteOrgCodeRepo;
import ru.aofoms.repository.InviteOrgRepo;
import ru.aofoms.service.InviteOrgService;

import java.util.List;

@Service
public class DefaultInviteOrgService implements InviteOrgService {

    private final InviteOrgRepo inviteOrgRepo;
    private final InviteOrgCodeRepo inviteOrgCodeRepo;

    public DefaultInviteOrgService(InviteOrgRepo inviteOrgRepo, InviteOrgCodeRepo inviteOrgCodeRepo) {
        this.inviteOrgRepo = inviteOrgRepo;
        this.inviteOrgCodeRepo = inviteOrgCodeRepo;
    }

    @Override
    public List<InviteOrgCode> findAllInviteOrgCode() {
        return inviteOrgCodeRepo.findAll();
    }

    @Override
    public List<InviteOrg> findAll() {
        return inviteOrgRepo.findAll();
    }

    @Override
    public void save(InviteOrg object) {
        inviteOrgRepo.save(object);
    }

    @Override
    public void delete(InviteOrg object) {
        inviteOrgRepo.delete(object);
    }

    @Override
    public InviteOrg findById(long id) {
        return inviteOrgRepo.findById(id).get();
    }
}
