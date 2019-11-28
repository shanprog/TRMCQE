package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.Diploma;
import ru.aofoms.repository.DiplomaRepo;
import ru.aofoms.service.DiplomaService;

import java.util.List;

@Service
public class DefaultDiplomaService implements DiplomaService {

    private final DiplomaRepo diplomaRepo;

    public DefaultDiplomaService(DiplomaRepo diplomaRepo) {
        this.diplomaRepo = diplomaRepo;
    }

    @Override
    public List<Diploma> findByExpertId(long expertId) {
        return diplomaRepo.findByExpertId(expertId);
    }

    @Override
    public void save(Diploma diploma) {
        diplomaRepo.save(diploma);
    }

    @Override
    public void delete(Diploma diploma) {
        diplomaRepo.delete(diploma);
    }
}
