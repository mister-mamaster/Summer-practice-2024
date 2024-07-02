package ru.opencode.bankinfo.messages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.opencode.bankinfo.messages.entity.subClass.AccRstr;

public interface AccRstrRepository extends JpaRepository<AccRstr, Long> {
}
