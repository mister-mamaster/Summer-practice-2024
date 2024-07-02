package ru.opencode.bankinfo.messages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.opencode.bankinfo.messages.entity.subClass.Rstr;

public interface RstrRepository extends JpaRepository<Rstr, Long> {
}
