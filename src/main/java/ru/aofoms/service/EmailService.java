package ru.aofoms.service;

import ru.aofoms.entity.Email;

import java.util.List;

public interface EmailService {

    List<Email> findByExpertId(long expertId);

    void save(Email email);

    void delete(Email email);
}
