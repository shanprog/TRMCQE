package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.Email;
import ru.aofoms.repository.EmailRepo;
import ru.aofoms.service.EmailService;

import java.util.List;

@Service
public class DefaultEmailService implements EmailService {

    private final EmailRepo emailRepo;

    public DefaultEmailService(EmailRepo emailRepo) {
        this.emailRepo = emailRepo;
    }

    @Override
    public List<Email> findByExpertId(long expertId) {
        return emailRepo.findByExpertId(expertId);
    }

    @Override
    public void save(Email email) {
        emailRepo.save(email);
    }

    @Override
    public void delete(Email email) {
        emailRepo.delete(email);
    }
}
