package com.danilo.springboot3.repository;

import com.danilo.springboot3.domain.UserRole;
import com.danilo.springboot3.dto.UserRoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository extends JpaRepository<UserRole,String> {

    @Modifying
    @Query(value =
        "INSERT INTO user_role(id,user_id,role_id) " +
        "VALUES (uuid(),:#{#userRole.userId},:#{#userRole.roleId})"
    ,nativeQuery = true)
    void insert(UserRoleDTO userRole);

}
