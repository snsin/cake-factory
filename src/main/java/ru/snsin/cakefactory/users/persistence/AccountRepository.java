package ru.snsin.cakefactory.users.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<AccountEntity, String> {
    @SuppressWarnings("NullableProblems")
    List<AccountEntity> findAll();
}
