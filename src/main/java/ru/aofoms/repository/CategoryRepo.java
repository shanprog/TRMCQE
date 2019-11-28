package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aofoms.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
