package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.Exclusion;
import ru.aofoms.entity.ExclusionReason;
import ru.aofoms.entity.Inclusion;
import ru.aofoms.repository.ExclusionReasonRepo;
import ru.aofoms.repository.ExclusionRepo;
import ru.aofoms.repository.InclusionRepo;
import ru.aofoms.service.ExclusionService;

import java.time.LocalDate;
import java.util.List;

@Service
public class DefaultExclusionService implements ExclusionService {

    private final ExclusionRepo exclusionRepo;
    private final ExclusionReasonRepo exclusionReasonRepo;
    private final InclusionRepo inclusionRepo;

    public DefaultExclusionService(ExclusionRepo exclusionRepo, ExclusionReasonRepo exclusionReasonRepo, InclusionRepo inclusionRepo) {
        this.exclusionRepo = exclusionRepo;
        this.exclusionReasonRepo = exclusionReasonRepo;
        this.inclusionRepo = inclusionRepo;
    }

    @Override
    public List<Exclusion> findByExpertId(long expertId) {
        return exclusionRepo.findByExpertId(expertId);
    }

    @Override
    public void save(Exclusion exclusion) {
        exclusionRepo.save(exclusion);
    }

    @Override
    public void delete(Exclusion exclusion) {
        exclusionRepo.delete(exclusion);
    }

    @Override
    public List<ExclusionReason> getExclusionReasons() {
        return exclusionReasonRepo.findAll();
    }

    @Override
    public List<Inclusion> findExpertInclusions(long expertId) {
        return inclusionRepo.findByExpertId(expertId);
    }

    @Override
    public LocalDate findLastExclusionDate(long expertId) {
        return exclusionRepo.findLastExclusionDate(expertId);
    }

    @Override
    public Exclusion findLastExclusion(long id) {
        return exclusionRepo.findLastExclusion(id);
    }
}
