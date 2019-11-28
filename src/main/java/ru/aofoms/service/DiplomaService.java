package ru.aofoms.service;

import ru.aofoms.entity.Diploma;

import java.util.List;

public interface DiplomaService {

    List<Diploma> findByExpertId(long expertId);

    void save(Diploma diploma);

    void delete(Diploma diploma);
}
