package ru.snsin.cakefactory.users.persistence;

import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.users.User;
import ru.snsin.cakefactory.users.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class JpaUserServiceImpl implements UserService {
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
