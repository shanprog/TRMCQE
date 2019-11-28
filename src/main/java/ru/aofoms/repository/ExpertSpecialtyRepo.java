package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.ExpertSpecialty;

import java.util.List;

public interface ExpertSpecialtyRepo extends JpaRepository<ExpertSpecialty, Long> {

    @Query("SELECT es FROM ExpertSpecialty es WHERE es.expert.id=:id")
    List<ExpertSpecialty> findByExpertId(@Param("id") long id);
}
