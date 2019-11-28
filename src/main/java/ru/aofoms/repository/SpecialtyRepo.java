package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aofoms.entity.Specialty;

public interface SpecialtyRepo extends JpaRepository<Specialty, Long> {
}
