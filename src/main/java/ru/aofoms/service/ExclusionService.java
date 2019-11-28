package ru.aofoms.service;

import ru.aofoms.entity.Exclusion;
import ru.aofoms.entity.ExclusionReason;
import ru.aofoms.entity.Inclusion;

import java.time.LocalDate;
import java.util.List;

public interface ExclusionService {

    List<Exclusion> findByExpertId(long expertId);

    void save(Exclusion exclusion);

    void delete(Exclusion exclusion);

    List<ExclusionReason> getExclusionReasons();

    List<Inclusion> findExpertInclusions(long expertId);

    LocalDate findLastExclusionDate(long expertId);

    Exclusion findLastExclusion(long id);
}
