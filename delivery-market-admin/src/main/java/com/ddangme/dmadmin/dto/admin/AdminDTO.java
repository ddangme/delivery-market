package com.ddangme.dmadmin.dto.admin;

import com.ddangme.dmadmin.model.admin.Admin;
import com.ddangme.dmadmin.model.constants.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@AllArgsConstructor
public class AdminDTO {
    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private Set<AdminRole> roles;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public static AdminDTO fromEntity(Admin entity) {
        return new AdminDTO(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getNickname(),
                entity.getRoles(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    public AdminDTO(Long id, String email, String password, String name, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }

    public Admin toEntity() {
        return new Admin(
                id,
                email,
                password,
                name,
                nickname,
                roles,
                createdAt,
                updatedAt,
                deletedAt
        );
    }
}
