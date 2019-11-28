package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.Email;

import java.util.List;

public interface EmailRepo extends JpaRepository<Email, Long> {

    @Query("SELECT e FROM Email e WHERE e.expert.id=:id")
    List<Email> findByExpertId(@Param("id") long id);
}
