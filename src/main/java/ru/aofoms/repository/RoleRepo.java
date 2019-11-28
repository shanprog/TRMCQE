package ru.aofoms.repository;

import org.springframework.data.repository.CrudRepository;
import ru.aofoms.entity.Role;

public interface RoleRepo extends CrudRepository<Role, Long> {

}
