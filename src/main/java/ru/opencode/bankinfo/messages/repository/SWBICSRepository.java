package ru.opencode.bankinfo.messages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.opencode.bankinfo.messages.entity.SWBIC;

public interface SWBICSRepository extends JpaRepository<SWBIC, Long> {
}
