package com.danilo.springboot3.repository;

import com.danilo.springboot3.domain.Person;
import com.danilo.springboot3.dto.PersonDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,String> {

    @Modifying
    @Query(value =
        "INSERT INTO person(id,name,cpf) " +
        "VALUES (uuid(),:#{#person.name},:#{#person.cpf})"
    ,nativeQuery = true)
    void insert(PersonDTO person);

    @Modifying
    @Query(value =
        "UPDATE person " +
        "SET name = :#{#person.name},cpf = :#{#person.cpf} " +
        "WHERE id = :#{#person.id}"
    ,nativeQuery = true)
    void update(PersonDTO person);

    @Modifying
    @Query(value =
        "DELETE FROM person " +
        "WHERE id = :id"
    ,nativeQuery = true)
    void remove(String id);

    @Query(value =
        "SELECT * " +
        "FROM person " +
        "ORDER BY name"
    ,nativeQuery = true)
    List<Person> getAll();

    @Query(value =
        "SELECT * " +
        "FROM person " +
        "WHERE id = :id " +
        "OR cpf = :cpf"
    ,nativeQuery = true)
    Person get(String id,String cpf);

    @Query(value =
        "SELECT CASE WHEN COUNT(id) > 0 THEN 'true' ELSE 'false' END " +
        "FROM person " +
        "WHERE id = :#{#person.id} " +
        "OR cpf = :#{#person.cpf}"
    ,nativeQuery = true)
    Boolean exists(PersonDTO person);

}
