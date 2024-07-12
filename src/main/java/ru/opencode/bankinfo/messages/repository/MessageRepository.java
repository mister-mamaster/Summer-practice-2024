package ru.opencode.bankinfo.messages.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.opencode.bankinfo.messages.entity.EMessageEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Transactional(propagation = Propagation.SUPPORTS)
@Repository
public interface MessageRepository extends JpaRepository<EMessageEntity, Long> {
    Boolean existsByBusinessDayEqualsAndIsDeletedEquals(LocalDate date, Boolean isDeleted);
    Page<EMessageEntity> findAllByeMessageNameContainsAndCreateDateTimeGreaterThanEqualAndCreateDateTimeLessThanEqualAndIsDeletedEquals(String eMessageName, LocalDateTime localDateTimeStart, LocalDateTime localDateTimeEnd,Boolean isDeleted, Pageable pageable);


}
