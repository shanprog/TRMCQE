package ru.aofoms.service;

import ru.aofoms.entity.Workplace;

import java.util.List;

public interface WorkplaceService {

    List<Workplace> findByExpertId(long expertId);

    void save(Workplace workplace);

    void delete(Workplace workplace);

    Workplace findById(long id);
}
