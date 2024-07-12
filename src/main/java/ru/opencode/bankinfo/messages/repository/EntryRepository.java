package ru.opencode.bankinfo.messages.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.opencode.bankinfo.messages.entity.Entry;

@Transactional(propagation = Propagation.SUPPORTS)
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    Page<Entry> findEntryByMessageIdAndParticipantInfo_PtTypeEqualsAndParticipantInfo_NamePContainsAndBICContains(Long emessageId, Byte participantType, String nameP, String bic, Pageable pageable);

    Page<Entry> findEntryByMessageIdAndParticipantInfo_NamePContainsAndBICContains(Long emessageId, String nameP, String bic, Pageable pageable);
}
