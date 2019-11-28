package ru.aofoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aofoms.entity.User;


public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.login=:login AND u.password=:password")
    User findByNameAndPassword(@Param("login") String login, @Param("password") String password);
}
