package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.Workplace;

import java.util.List;

public interface WorkplaceRepo extends JpaRepository<Workplace, Long> {

    @Query("SELECT w FROM Workplace w WHERE w.expert.id=:id")
    List<Workplace> findByExpertId(@Param("id") long id);
}
