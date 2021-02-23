package ru.snsin.cakefactory.users.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    @SuppressWarnings("NullableProblems")
    List<UserEntity> findAll();
}
