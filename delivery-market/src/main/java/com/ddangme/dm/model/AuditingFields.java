package com.ddangme.dm.model;

import com.ddangme.dm.model.member.Member;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditingFields {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    protected LocalDateTime createdAt;

    @CreatedBy
    @JoinColumn(name = "created_by")
    @ManyToOne
    protected Member createdBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    protected LocalDateTime updatedAt;


    @LastModifiedBy
    @JoinColumn(name = "updated_by")
    @ManyToOne
    protected Member updatedBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    protected LocalDateTime deletedAt;

    @JoinColumn(name = "deleted_by")
    @ManyToOne
    protected Member deletedBy;

    public void delete(Member member) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = member;
    }

}
