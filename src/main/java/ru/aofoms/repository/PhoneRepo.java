package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.Phone;

import java.util.List;

public interface PhoneRepo extends JpaRepository<Phone, Long> {

    @Query("SELECT p FROM Phone p WHERE p.expert.id=:id")
    List<Phone> findByExpertId(@Param("id") long id);

}
