package ru.aofoms.service.impl;

import org.springframework.stereotype.Service;
import ru.aofoms.entity.User;
import ru.aofoms.repository.UserRepo;
import ru.aofoms.service.UserService;
import ru.aofoms.util.Action;
import ru.aofoms.util.SaveInfo;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

    private final UserRepo repository;

    public DefaultUserService(UserRepo repository) {
        this.repository = repository;
    }

    @Override
    public User findUserByNameAndPassword(String name, String password) {
        return repository.findByNameAndPassword(name, password);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void updateUsers(List<SaveInfo> list) {
        list.forEach(e -> {
            if (e.getAction() == Action.ADD || e.getAction() == Action.EDIT) {
                repository.save((User) e.getObject());
            }
            if (e.getAction() == Action.DELETE) {
                repository.delete((User) e.getObject());
            }
        });
    }
}
