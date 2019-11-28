package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.Inclusion;
import ru.aofoms.repository.InclusionRepo;
import ru.aofoms.repository.InviteOrgRepo;
import ru.aofoms.service.InclusionService;

import java.time.LocalDate;
import java.util.List;

@Service
public class DefaultInclusionService implements InclusionService {

    private final InclusionRepo inclusionRepo;
    private final InviteOrgRepo inviteOrgRepo;

    public DefaultInclusionService(InclusionRepo inclusionRepo, InviteOrgRepo inviteOrgRepo) {
        this.inclusionRepo = inclusionRepo;
        this.inviteOrgRepo = inviteOrgRepo;
    }

    @Override
    public List<Inclusion> findByExpertId(long expertId) {
        return inclusionRepo.findByExpertId(expertId);
    }

    @Override
    public void save(Inclusion inclusion) {
//        inviteOrgRepo.save(inclusion.getInviteOrg());
        inclusionRepo.save(inclusion);
    }

    @Override
    public void delete(Inclusion inclusion) {
        inclusionRepo.delete(inclusion);
    }

    @Override
    public LocalDate findLastInclusionDate(long expertId) {
        return inclusionRepo.findLastInclusionDate(expertId);
    }

    @Override
    public Inclusion findLastInclusion(long expertId) {
        return inclusionRepo.findLastInclusion(expertId);
    }
}
