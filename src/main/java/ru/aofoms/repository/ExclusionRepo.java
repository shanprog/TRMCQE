package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.Exclusion;

import java.time.LocalDate;
import java.util.List;

public interface ExclusionRepo extends JpaRepository<Exclusion, Long> {

    @Query("SELECT e FROM Exclusion e WHERE e.expert.id=:id")
    List<Exclusion> findByExpertId(@Param("id") long id);

    @Query("SELECT MAX(e.date) FROM Exclusion e WHERE e.expert.id=:id")
    LocalDate findLastExclusionDate(@Param("id") long expertId);

    @Query("SELECT e FROM Exclusion e WHERE e.date IN (SELECT MAX(te.date) FROM Exclusion te WHERE te.expert.id=:expertId) AND e.expert.id=:expertId")
    Exclusion findLastExclusion(@Param("expertId") long expertId);
}
