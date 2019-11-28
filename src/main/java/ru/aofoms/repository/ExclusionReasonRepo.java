package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aofoms.entity.ExclusionReason;

public interface ExclusionReasonRepo extends JpaRepository<ExclusionReason, Long> {

}
