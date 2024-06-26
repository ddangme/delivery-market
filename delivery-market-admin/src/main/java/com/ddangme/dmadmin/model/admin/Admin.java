package com.ddangme.dmadmin.model.admin;

import com.ddangme.dmadmin.converter.RoleTypesConverter;
import com.ddangme.dmadmin.model.constants.AdminRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE admin SET deleted_at = NOW() WHERE id = ?")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickname;

    @Column(name = "role")
    @Convert(converter = RoleTypesConverter.class)
    private Set<AdminRole> roles = new LinkedHashSet<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreatedDate
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime deletedAt;

    public Admin(Long id, String email, String password, String name, String nickname, LocalDateTime deletedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.deletedAt = deletedAt;
    }
}
