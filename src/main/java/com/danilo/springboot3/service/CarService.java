package com.danilo.springboot3.service;

import com.danilo.springboot3.domain.Car;
import com.danilo.springboot3.dto.CarDTO;
import com.danilo.springboot3.design_pattern.FacadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class CarService {

    @Autowired
    private FacadeRepository repository;

    @Transactional
    public void insert(CarDTO car) {
        if (this.repository.car.exists(car)) {
            String msg = "Carro já cadastrado para essa pessoa na base de dados.";
            throw new ResponseStatusException(HttpStatus.CONFLICT,msg);
        }

        this.repository.car.insert(car);
    }

    @Transactional
    public void update(CarDTO car) {
        if (!this.repository.car.exists(car)) {
            String msg = "Carro não cadastrado na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        this.repository.car.update(car);
    }

    @Transactional
    public void remove(String id) {
        CarDTO car = new CarDTO();
        car.setId(id);

        if (!this.repository.car.exists(car)) {
            String msg = "Carro não cadastrado na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        this.repository.car.remove(id);
    }

    public List<Car> getAll() {
        List<Car> cars = this.repository.car.getAll();

        if (cars.isEmpty()) {
            String msg = "Não há carros cadastrados na base de dados.";
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,msg);
        }

        return cars;
    }

    public Car get(String id) {
        CarDTO dto = new CarDTO();
        dto.setId(id);
        Car car = this.repository.car.get(dto);

        if (Objects.isNull(car)) {
            String msg = "Carro não cadastrado na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        return car;
    }

}
