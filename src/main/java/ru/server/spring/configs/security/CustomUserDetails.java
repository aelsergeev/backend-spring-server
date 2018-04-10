package ru.server.spring.configs.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.server.spring.models.Permission;
import ru.server.spring.models.User;

import java.util.*;

public class CustomUserDetails extends User implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        super(user);
        this.user = user;
    }

    public Map<String, Boolean> getAuthoritiesMap() {
        Map<String, Boolean> authorities = new HashMap<>();

        for (Permission permission : this.user.getPermissions())
            authorities.put("ROLE_" + permission.getName().toUpperCase(), true);

        if (this.user.getPosition() != null) {
            for (Permission permission : this.user.getPosition().getPermissions())
                authorities.put("ROLE_" + permission.getName().toUpperCase(), true);
        }

        return authorities;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Permission permission : this.user.getPermissions())
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + permission.getName().toUpperCase()));

        if (this.user.getPosition() != null) {
            for (Permission permission : this.user.getPosition().getPermissions())
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + permission.getName().toUpperCase()));
        }

        return grantedAuthorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
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
    public boolean equals(Object o) {
        return o instanceof CustomUserDetails && user.getUsername().equals(((User) o).getUsername());
    }

    @Override
    public int hashCode() {
        return user.getUsername().hashCode();
    }

}
