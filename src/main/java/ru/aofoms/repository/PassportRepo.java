package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.Passport;

import java.util.List;

public interface PassportRepo extends JpaRepository<Passport, Long> {

    @Query("SELECT p FROM Passport p WHERE p.expert.id=:id")
    List<Passport> findByExpertId(@Param("id") long id);

    @Query("SELECT p FROM Passport p WHERE p.outDate IN (SELECT MAX(tp.outDate) FROM Passport tp WHERE tp.expert.id=:expertId)")
    Passport findLastPassport(@Param("expertId") long expertId);
}
