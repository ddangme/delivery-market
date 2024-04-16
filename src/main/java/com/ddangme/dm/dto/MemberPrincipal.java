package com.ddangme.dm.dto;

import com.ddangme.dm.model.member.MemberStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class MemberPrincipal implements UserDetails {
    private Long id;
    private String loginId;
    private String password;
    private String name;

    private MemberStatus memberStatus;


    public static MemberPrincipal of(Long id, String loginId, String password, String name, MemberStatus memberStatus) {
        return new MemberPrincipal(id, loginId, password, name, memberStatus);
    }

    public static MemberPrincipal fromDTO(MemberDTO dto) {
        return MemberPrincipal.of(dto.getId(), dto.getLoginId(), dto.getPassword(), dto.getName(), dto.getMemberStatus());
    }



    @Override public String getUsername() { return loginId; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}
