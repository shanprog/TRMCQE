package ru.aofoms.service;

import ru.aofoms.entity.Expertise;

import java.util.List;

public interface ExpertiseService {
    List<Expertise> findBySpecialty(long expertSpecId);

    void save(Expertise expertise);

    void delete(Expertise expertise);

    List<Expertise> findBySpecialtyForXLS(long expertSpecId, int i);
}
