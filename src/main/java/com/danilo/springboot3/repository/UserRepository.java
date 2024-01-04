package com.danilo.springboot3.repository;

import com.danilo.springboot3.domain.User;
import com.danilo.springboot3.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    @Modifying
    @Query(value =
        "INSERT INTO `user`(id,username,password) " +
        "VALUES (uuid(),:#{#user.username},:#{#user.password})"
    ,nativeQuery = true)
    void insert(UserDTO user);

    @Modifying
    @Query(value =
        "UPDATE `user` " +
        "SET username = :#{#user.username},password = :#{#user.password} " +
        "WHERE id = :#{#user.id}"
    ,nativeQuery = true)
    void update(UserDTO user);

    @Modifying
    @Query(value =
        "DELETE FROM `user` " +
        "WHERE id = :id"
    ,nativeQuery = true)
    void remove(String id);

    @Query(value =
        "SELECT * " +
        "FROM `user` " +
        "ORDER BY username"
    ,nativeQuery = true)
    List<User> getAll();

    @Query(value =
        "SELECT * " +
        "FROM `user` " +
        "WHERE id = :#{#user.id} " +
        "OR username = :#{#user.username}"
    ,nativeQuery = true)
    User get(UserDTO user);

    @Query(value =
        "SELECT CASE WHEN COUNT(id) > 0 THEN 'true' ELSE 'false' END " +
        "FROM `user` " +
        "WHERE id = :#{#user.id} " +
        "OR username = :#{#user.username}"
    ,nativeQuery = true)
    Boolean exists(UserDTO user);

}
