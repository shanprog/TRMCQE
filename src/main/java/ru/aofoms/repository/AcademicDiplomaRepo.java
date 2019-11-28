package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.AcademicDiploma;

import java.util.List;

public interface AcademicDiplomaRepo extends JpaRepository<AcademicDiploma, Long> {

    @Query("SELECT ad FROM AcademicDiploma ad WHERE ad.expert.id=:id")
    List<AcademicDiploma> findByExpertId(@Param("id") long id);
}
