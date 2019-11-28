package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.Expertise;

import java.util.List;

public interface ExpertiseRepo extends JpaRepository<Expertise, Long> {

    @Query("SELECT e FROM Expertise e WHERE e.specialty.id=:id")
    List<Expertise> findBySpecialty(@Param("id") long expertSpecId);

    @Query("SELECT e FROM Expertise e WHERE e.specialty.id=:id AND e.year=:year")
    List<Expertise> findBySpecialtyForXLS(@Param("id") long expertSpecId, @Param("year")int year);
}
