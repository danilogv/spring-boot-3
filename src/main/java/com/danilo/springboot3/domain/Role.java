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

import java.io.Serializable;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"name"})
public class Role implements GrantedAuthority,Serializable {

    @Id
    @Column(name = "id",nullable = false,unique = true)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name",nullable = false,unique = true)
    private Permission name;

    @Override
    public String getAuthority() {
        return this.name.toString();
    }

}
