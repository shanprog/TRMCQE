package ru.aofoms.service;

import ru.aofoms.entity.User;
import ru.aofoms.util.SaveInfo;

import java.util.List;

public interface UserService {

    User findUserByNameAndPassword(String name, String password);

    List<User> findAll();

    void updateUsers(List<SaveInfo> list);
}
