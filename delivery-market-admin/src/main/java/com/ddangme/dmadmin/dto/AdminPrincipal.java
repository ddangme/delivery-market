package com.ddangme.dmadmin.dto;

import com.ddangme.dmadmin.model.constants.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class AdminPrincipal implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private LocalDateTime deletedAt;
    Collection<? extends GrantedAuthority> roles;

    public static AdminPrincipal of(Long id, String email, String password, String name, String nickname, Set<AdminRole> roles, LocalDateTime deletedAt) {
        return new AdminPrincipal(id, email, password, name, nickname, deletedAt,
                roles.stream()
                        .map(AdminRole::getRole)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()));
    }

    public static AdminPrincipal fromDTO(AdminDTO dto) {
        return AdminPrincipal.of(
                dto.getId(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getName(),
                dto.getNickname(),
                dto.getRoles(),
                dto.getDeletedAt()
        );
    }


    @Override public String getUsername() { return nickname; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    // TODO: 설정 필요
    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }
    @Override public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override public boolean isEnabled() { return true; }

}
