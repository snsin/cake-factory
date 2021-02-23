package ru.snsin.cakefactory.users.persistence;

import org.springframework.stereotype.Service;
import ru.snsin.cakefactory.users.User;
import ru.snsin.cakefactory.users.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public JpaUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        final List<UserEntity> all = userRepository.findAll();
        return all.stream()
                .map(this::mapEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        final Optional<UserEntity> userEntity = userRepository.findById(email);
        return userEntity.map(this::mapEntityToUser);
    }

    @Override
    public void save(User user) {
        Objects.requireNonNull(user);
        userRepository.save(mapUserToUserEntity(user));
    }

    private User mapEntityToUser(UserEntity userEntity) {
        return new User(userEntity.getEmail(), userEntity.getPassword());
    }

    private UserEntity mapUserToUserEntity(User user) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        return userEntity;
    }
}
