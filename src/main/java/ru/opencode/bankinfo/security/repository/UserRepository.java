package ru.opencode.bankinfo.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.opencode.bankinfo.security.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
}
