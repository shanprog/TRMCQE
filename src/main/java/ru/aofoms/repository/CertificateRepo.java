package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.Certificate;

import java.util.List;

public interface CertificateRepo extends JpaRepository<Certificate, Long> {

    @Query("SELECT c FROM Certificate c WHERE c.specialty.id=:id")
    List<Certificate> findBySpecialty(@Param("id") long expertSpecId);

}
