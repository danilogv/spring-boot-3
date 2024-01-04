package com.danilo.springboot3.repository;

import com.danilo.springboot3.domain.Car;
import com.danilo.springboot3.dto.CarDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,String> {

    @Modifying
    @Query(value =
        "INSERT INTO car(id,name,brand,`year`,price,person_id) " +
        "VALUES (uuid(),:#{#car.name},:#{#car.brand},:#{#car.year},:#{#car.price},:#{#car.personId})"
    ,nativeQuery = true)
    void insert(CarDTO car);

    @Modifying
    @Query(value =
        "UPDATE car " +
        "SET name = :#{#car.name},brand = :#{#car.brand},`year` = :#{#car.year},price = :#{#car.price},person_id = :#{#car.personId} " +
        "WHERE id = :#{#car.id}"
    ,nativeQuery = true)
    void update(CarDTO car);

    @Modifying
    @Query(value =
        "DELETE FROM car " +
        "WHERE id = :id"
    ,nativeQuery = true)
    void remove(String id);

    @Query(value =
        "SELECT * " +
        "FROM car " +
        "ORDER BY name"
    ,nativeQuery = true)
    List<Car> getAll();

    @Query(value =
        "SELECT * " +
        "FROM car " +
        "WHERE id = :#{#car.id} " +
        "OR (name = :#{#car.name} AND brand = :#{#car.brand} AND `year` = :#{#car.year} AND person_id = :#{#car.personId})"
    ,nativeQuery = true)
    Car get(CarDTO car);

    @Query(value =
        "SELECT CASE WHEN COUNT(id) > 0 THEN 'true' ELSE 'false' END " +
        "FROM car " +
        "WHERE id = :#{#car.id} " +
        "OR (name = :#{#car.name} AND brand = :#{#car.brand} AND `year` = :#{#car.year} AND person_id = :#{#car.personId})"
    ,nativeQuery = true)
    Boolean exists(CarDTO car);

}
