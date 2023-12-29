package com.danilo.springboot3.controller;

import com.danilo.springboot3.domain.Car;
import com.danilo.springboot3.dto.CarDTO;
import com.danilo.springboot3.design_pattern.FacadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/carro")
public class CarController {

    @Autowired
    private FacadeService service;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid CarDTO car) {
        this.service.car.insert(car);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid CarDTO car) {
        this.service.car.update(car);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable String id) {
        this.service.car.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        List<Car> cars = this.service.car.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> get(@PathVariable String id) {
        Car car = this.service.car.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

}
