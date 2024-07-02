package ru.opencode.bankinfo.util;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Audit {
//    @CreatedBy
//    private Long createdBy;

    @CreatedDate
    private LocalDateTime createDateTime;

//    @LastModifiedBy
//    private Long changedBy;

    @LastModifiedDate
    private LocalDateTime changeDateTime;

    private Boolean isDeleted = false;

}

