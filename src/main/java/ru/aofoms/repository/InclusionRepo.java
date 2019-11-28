package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.Inclusion;

import java.time.LocalDate;
import java.util.List;

public interface InclusionRepo extends JpaRepository<Inclusion, Long> {

    @Query("SELECT i FROM Inclusion i WHERE i.expert.id=:id")
    List<Inclusion> findByExpertId(@Param("id") long id);

    @Query("SELECT MAX(i.date) FROM Inclusion i WHERE i.expert.id=:id")
    LocalDate findLastInclusionDate(@Param("id") long id);

    @Query("SELECT i FROM Inclusion i WHERE i.date IN (SELECT MAX(ti.date) FROM Inclusion ti WHERE ti.expert.id=:expertId) AND i.expert.id=:expertId")
    Inclusion findLastInclusion(@Param("expertId") long expertId);
}
