package ru.opencode.bankinfo.manuals.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.opencode.bankinfo.manuals.entity.Manual;


@Repository
public interface ManualRepository extends JpaRepository<Manual, Long> {
    Page<Manual> findByInfo_idAndIsDeletedEqualsAndCodeContainsAndDescriptionContains(Long id, Boolean isDeleted, String code, String description, Pageable pageable);

}
