package com.danilo.springboot3.repository;

import com.danilo.springboot3.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role,String> {

    @Query(value =
        "SELECT * " +
        "FROM role " +
        "WHERE name = :name"
    ,nativeQuery = true)
    Role get(String name);

}
