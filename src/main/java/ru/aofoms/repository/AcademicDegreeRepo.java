package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aofoms.entity.AcademicDegree;

public interface AcademicDegreeRepo extends JpaRepository<AcademicDegree, Long> {
}
