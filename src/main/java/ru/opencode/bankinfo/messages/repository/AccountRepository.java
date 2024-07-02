package ru.opencode.bankinfo.messages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.opencode.bankinfo.messages.entity.Account;
public interface AccountRepository extends JpaRepository<Account, Long> {
}
