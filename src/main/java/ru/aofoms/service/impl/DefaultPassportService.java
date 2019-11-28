package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.Passport;
import ru.aofoms.repository.PassportRepo;
import ru.aofoms.service.PassportService;

import java.util.List;

@Service
public class DefaultPassportService implements PassportService {

    private final PassportRepo passportRepo;

    public DefaultPassportService(PassportRepo passportRepo) {
        this.passportRepo = passportRepo;
    }

    @Override
    public List<Passport> findByExpertId(long expertId) {
        return passportRepo.findByExpertId(expertId);
    }

    @Override
    public void save(Passport passport) {
        passportRepo.save(passport);
    }

    @Override
    public void delete(Passport passport) {
        passportRepo.delete(passport);
    }

    @Override
    public Passport findLastPassport(long expertId) {
        return passportRepo.findLastPassport(expertId);
    }
}
