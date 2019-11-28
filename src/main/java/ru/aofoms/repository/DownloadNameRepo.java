package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.DownloadName;

public interface DownloadNameRepo extends JpaRepository<DownloadName, Long> {

    @Query(value = "SELECT MAX(file_number) FROM download_names WHERE extract(MONTH FROM download_date)=extract(MONTH FROM now()) AND file_type=:fType", nativeQuery = true)
    Integer getMaxNumber(@Param("fType") String fType);

}
