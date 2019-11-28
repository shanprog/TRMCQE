package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.Expertise;
import ru.aofoms.repository.ExpertiseRepo;
import ru.aofoms.service.ExpertiseService;

import java.util.List;

@Service
public class DefaultExpertiseService implements ExpertiseService {

    private final ExpertiseRepo expertiseRepo;

    public DefaultExpertiseService(ExpertiseRepo expertiseRepo) {
        this.expertiseRepo = expertiseRepo;
    }

    @Override
    public List<Expertise> findBySpecialty(long expertSpecId) {
        return expertiseRepo.findBySpecialty(expertSpecId);
    }

    @Override
    public void save(Expertise expertise) {
        expertiseRepo.save(expertise);
    }

    @Override
    public void delete(Expertise expertise) {
        expertiseRepo.delete(expertise);
    }

    @Override
    public List<Expertise> findBySpecialtyForXLS(long expertSpecId, int year) {
        return expertiseRepo.findBySpecialtyForXLS(expertSpecId, year);
    }
}
