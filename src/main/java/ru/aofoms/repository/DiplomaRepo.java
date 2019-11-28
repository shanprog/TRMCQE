package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.Diploma;

import java.util.List;

public interface DiplomaRepo extends JpaRepository<Diploma, Long> {

    @Query("SELECT d FROM Diploma d WHERE d.expert.id=:id")
    List<Diploma> findByExpertId(@Param("id") long id);
}
