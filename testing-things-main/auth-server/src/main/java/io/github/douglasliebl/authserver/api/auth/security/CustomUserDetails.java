package io.github.douglasliebl.authserver.api.auth.security;

import io.github.douglasliebl.authserver.api.user.model.entity.Role;
import io.github.douglasliebl.authserver.api.user.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User implements UserDetails {

    private String email;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User byEmail) {
        this.email = byEmail.getEmail();
        this.password = byEmail.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();

        for (Role role: byEmail.getRoles()) {
            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }

        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
