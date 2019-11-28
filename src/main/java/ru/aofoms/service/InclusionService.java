package ru.aofoms.service;

import ru.aofoms.entity.Inclusion;
import ru.aofoms.entity.InviteOrgCode;

import java.time.LocalDate;
import java.util.List;

public interface InclusionService {

    List<Inclusion> findByExpertId(long expertId);

    void save(Inclusion inclusion);

    void delete(Inclusion inclusion);

    LocalDate findLastInclusionDate(long expertId);

    Inclusion findLastInclusion(long expertId);
}
