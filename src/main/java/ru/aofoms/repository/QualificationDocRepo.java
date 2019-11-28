package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.QualificationDoc;

import java.time.LocalDate;
import java.util.List;

public interface QualificationDocRepo extends JpaRepository<QualificationDoc, Long> {

    @Query("SELECT qd FROM QualificationDoc qd WHERE qd.expert.id=:id")
    List<QualificationDoc> findByExpertId(@Param("id") long id);

    @Query("SELECT qd FROM QualificationDoc qd WHERE qd.specialty.id=:id")
    List<QualificationDoc> findBySpecialty(@Param("id") long id);
}
