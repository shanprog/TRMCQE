package ru.aofoms.service;

import ru.aofoms.entity.Passport;

import java.util.List;

public interface PassportService {

    List<Passport> findByExpertId(long expertId);

    void save(Passport passport);

    void delete(Passport passport);

    Passport findLastPassport(long expertId);
}
