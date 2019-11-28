package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.Phone;
import ru.aofoms.repository.PhoneRepo;
import ru.aofoms.service.PhoneService;

import java.util.List;

@Service
public class DefaultPhoneService implements PhoneService {

    private final PhoneRepo phoneRepo;

    public DefaultPhoneService(PhoneRepo phoneRepo) {
        this.phoneRepo = phoneRepo;
    }

    @Override
    public List<Phone> findByExpertId(long expertId) {
        return phoneRepo.findByExpertId(expertId);
    }

    @Override
    public void save(Phone phone) {
        phoneRepo.save(phone);
    }

    @Override
    public void delete(Phone phone) {
        phoneRepo.delete(phone);
    }
}
