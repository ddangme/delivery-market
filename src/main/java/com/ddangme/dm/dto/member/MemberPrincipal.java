package com.ddangme.dm.dto.member;

import com.ddangme.dm.model.member.BenefitLevel;
import com.ddangme.dm.model.member.MemberRole;
import com.ddangme.dm.model.member.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class MemberPrincipal implements UserDetails, OAuth2User {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private MemberStatus memberStatus;
    private BenefitLevel benefitLevel;
    Collection<? extends GrantedAuthority> authorities;
    Map<String, Object> oAuth2Attributes;


    public static MemberPrincipal of(Long id, String loginId, String password, String name, MemberStatus memberStatus, BenefitLevel benefitLevel) {
        return of(id, loginId, password, name, memberStatus, benefitLevel, Map.of());
    }

    public static MemberPrincipal of(Long id, String loginId, String password, String name, MemberStatus memberStatus, BenefitLevel benefitLevel, Map<String, Object> oAuth2Attributes) {
        return new MemberPrincipal(id, loginId, password, name, memberStatus, benefitLevel,
                Set.of(MemberRole.USER).stream()
                        .map(MemberRole::getType)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()), oAuth2Attributes);
    }


    public static MemberPrincipal fromDTO(MemberDTO dto) {
        return MemberPrincipal.of(dto.getId(), dto.getLoginId(), dto.getPassword(), dto.getName(), dto.getMemberStatus(), dto.getBenefitLevel());
    }



    @Override public String getUsername() { return name; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override public boolean isAccountNonExpired() {
        return memberStatus.equals(MemberStatus.NORMAL);
    }

    @Override public boolean isAccountNonLocked() {
        return memberStatus.equals(MemberStatus.NORMAL);
    }
    @Override public boolean isCredentialsNonExpired() { return memberStatus.equals(MemberStatus.NORMAL); }
    @Override public boolean isEnabled() { return true; }

    @Override public String getName() {
        return name;
    }

    @Override public Map<String, Object> getAttributes() {
        return oAuth2Attributes;
    }
}
