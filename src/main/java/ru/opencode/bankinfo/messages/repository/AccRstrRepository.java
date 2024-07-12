package ru.opencode.bankinfo.messages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.opencode.bankinfo.messages.entity.subClass.AccRstr;

@Transactional(propagation = Propagation.SUPPORTS)
@Repository
public interface AccRstrRepository extends JpaRepository<AccRstr, Long> {
}
