package com.danilo.springboot3.domain;

import com.danilo.springboot3.enumeration.Permission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_login")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"username"})
public class User implements UserDetails,GrantedAuthority,Serializable {

    @Id
    @Column(name = "id",nullable = false,unique = true)
    private String id;

    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission",nullable = false)
    private Permission permission;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> permissions = List.of("ADMIN","USER");
        List<SimpleGrantedAuthority> authorizations = new ArrayList<>();

        permissions.forEach((permission) -> {
            SimpleGrantedAuthority authorization = new SimpleGrantedAuthority(permission);
            authorizations.add(authorization);
        });

        return authorizations;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
    public String getAuthority() {
        return this.permission.name();
    }

}
