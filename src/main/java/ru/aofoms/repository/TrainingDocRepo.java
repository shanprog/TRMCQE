package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.TrainingDoc;

import java.util.List;

public interface TrainingDocRepo extends JpaRepository<TrainingDoc, Long> {

    @Query("SELECT td FROM TrainingDoc td WHERE td.expert.id=:id")
    List<TrainingDoc> findByExpertId(@Param("id") long id);
}
