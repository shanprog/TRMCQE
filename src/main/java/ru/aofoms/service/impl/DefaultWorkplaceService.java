package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.Workplace;
import ru.aofoms.repository.WorkplaceRepo;
import ru.aofoms.service.WorkplaceService;

import java.util.List;

@Service
public class DefaultWorkplaceService implements WorkplaceService {

    private final WorkplaceRepo workplaceRepo;

    public DefaultWorkplaceService(WorkplaceRepo workplaceRepo) {
        this.workplaceRepo = workplaceRepo;
    }

    @Override
    public List<Workplace> findByExpertId(long expertId) {
        return workplaceRepo.findByExpertId(expertId);
    }

    @Override
    public void save(Workplace workplace) {
        workplaceRepo.save(workplace);
    }

    @Override
    public void delete(Workplace workplace) {
        workplaceRepo.delete(workplace);
    }

    @Override
    public Workplace findById(long id) {
        return workplaceRepo.findById(id).get();
    }
}
