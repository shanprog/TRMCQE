package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.Expert;

import java.util.List;

public interface ExpertRepo extends JpaRepository<Expert, Long> {

    @Query("SELECT e.snils FROM Expert e WHERE e.snils=:snils")
    String findSnils(@Param("snils") String snils);

    @Query("SELECT e.number FROM Expert e WHERE e.number=:number")
    String findNumber(@Param("number") String number);

    @Query("SELECT MAX(e.number) FROM Expert e")
    String findMaxNumber();

    @Query("SELECT e FROM Expert e ORDER BY e.number")
    List<Expert> findAll();
}
