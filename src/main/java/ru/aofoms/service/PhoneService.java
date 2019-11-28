package ru.aofoms.service;

import ru.aofoms.entity.Phone;

import java.util.List;

public interface PhoneService {

    List<Phone> findByExpertId(long expertId);

    void save(Phone phone);

    void delete(Phone phone);

}
