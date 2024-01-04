package com.danilo.springboot3;

import com.danilo.springboot3.domain.Car;
import com.danilo.springboot3.domain.Person;
import com.danilo.springboot3.dto.CarDTO;
import com.danilo.springboot3.dto.PersonDTO;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarTest extends ObjectTest {

    private final String URL = "/carro";

    @Test
    public void car_findAll_status204() throws Exception {
        this.mock.perform(get(this.URL)).andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void car_findAll_status200() throws Exception {
        String name = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PersonDTO personDTO = this.getPerson(null,name,cpf);
        this.repository.person.insert(personDTO);
        Person pessoa = this.repository.person.get(null,cpf);

        String brand = "Chevrolet";
        name = "Celta";
        Integer year = 2008;
        BigDecimal price = new BigDecimal("14500.00");
        String personId = pessoa.getId();
        CarDTO car = this.getCar(null,name,brand,year,price,personId);
        this.repository.car.insert(car);
        this.mock.perform(get(this.URL)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void car_findOne_status200() throws Exception {
        String name = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PersonDTO personDTO = this.getPerson(null,name,cpf);
        this.repository.person.insert(personDTO);
        Person person = this.repository.person.get(null,cpf);

        String brand = "Chevrolet";
        name = "Celta";
        Integer year = 2008;
        BigDecimal price = new BigDecimal("14500.00");
        String personId = person.getId();
        CarDTO carDTO = this.getCar(null,name,brand,year,price,personId);
        this.repository.car.insert(carDTO);
        Car car = this.repository.car.get(carDTO);
        String url = this.URL + "/" + car.getId();
        this.mock.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void car_findOne_status400() throws Exception {
        String id = "1";
        String url = this.URL + "/" + id;
        this.mock.perform(get(url)).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void car_insert_status201() throws Exception {
        String name = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PersonDTO personDTO = this.getPerson(null,name,cpf);
        this.repository.person.insert(personDTO);
        Person person = this.repository.person.get(null,cpf);

        String brand = "Chevrolet";
        name = "Celta";
        Integer year = 2008;
        BigDecimal price = new BigDecimal("14500.00");
        String personId = person.getId();
        CarDTO car = this.getCar(null,name,brand,year,price,personId);
        String json = this.createJson(car).toString();
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void car_insert_status400() throws Exception {
        String name = "Celta";
        String brand = "Chevrolet";
        Integer year = 2008;
        BigDecimal price = BigDecimal.ZERO;
        String personId = "1";
        CarDTO car = this.getCar(null,name,brand,year,price,personId);
        String json = this.createJson(car).toString();
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void car_insert_status409() throws Exception {
        String name = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PersonDTO personDTO = this.getPerson(null,name,cpf);
        this.repository.person.insert(personDTO);
        Person person = this.repository.person.get(null,cpf);

        String brand = "Chevrolet";
        name = "Celta";
        Integer year = 2008;
        BigDecimal price = new BigDecimal("14500.00");
        String personId = person.getId();
        CarDTO carDTO = this.getCar(null,name,brand,year,price,personId);
        this.repository.car.insert(carDTO);

        name = "Celta";
        brand = "Chevrolet";
        year = 2008;
        price = new BigDecimal("20000.00");
        personId = person.getId();
        carDTO = this.getCar(null,name,brand,year,price,personId);
        String json = this.createJson(carDTO).toString();
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isConflict());
    }

    @Test
    @Transactional
    public void car_update_status204() throws Exception {
        String name = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PersonDTO personDTO = this.getPerson(null,name,cpf);
        this.repository.person.insert(personDTO);
        Person person = this.repository.person.get(null,cpf);

        String brand = "Chevrolet";
        name = "Celta";
        Integer year = 2008;
        BigDecimal price = new BigDecimal("14500.00");
        String personId = person.getId();
        CarDTO carDTO = this.getCar(null,name,brand,year,price,personId);
        this.repository.car.insert(carDTO);
        Car car = this.repository.car.get(carDTO);

        String carId = car.getId();
        name = "Celta";
        brand = "Chevrolet";
        year = 2013;
        price = new BigDecimal("20000.00");
        personId = person.getId();
        carDTO = this.getCar(carId,name,brand,year,price,personId);
        String json = this.createJson(carDTO).toString();
        String url = this.URL + "/" + car.getId();
        this.mock.perform(put(url).contentType(this.JSON).content(json)).andExpect(status().isNoContent());
    }

    @Test
    public void car_update_status400() throws Exception {
        String carId = "1";
        String name = "Celta";
        String brand = "Chevrolet";
        Integer year = 2013;
        BigDecimal price = new BigDecimal("20000.00");
        String personId = "1";
        CarDTO car = this.getCar(carId,name,brand,year,price,personId);
        String json = this.createJson(car).toString();
        String url = this.URL + "/" + carId;
        this.mock.perform(put(url).contentType(this.JSON).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void car_delete_status204() throws Exception {
        String name = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PersonDTO personDTO = this.getPerson(null,name,cpf);
        this.repository.person.insert(personDTO);
        Person person = this.repository.person.get(null,cpf);

        String brand = "Chevrolet";
        name = "Celta";
        Integer year = 2008;
        BigDecimal price = new BigDecimal("14500.00");
        String personId = person.getId();
        CarDTO carDTO = this.getCar(null,name,brand,year,price,personId);
        this.repository.car.insert(carDTO);
        Car car = this.repository.car.get(carDTO);
        String url = this.URL + "/" + car.getId();
        this.mock.perform(delete(url)).andExpect(status().isNoContent());
    }

    @Test
    public void car_delete_status400() throws Exception {
        String id = "1";
        String url = this.URL + "/" + id;
        this.mock.perform(delete(url)).andExpect(status().isBadRequest());
    }

    private JSONObject createJson(CarDTO car) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id",car.getId());
        json.put("name",car.getName());
        json.put("brand",car.getBrand());
        json.put("year",car.getYear());
        json.put("price",car.getPrice());
        json.put("personId",car.getPersonId());
        return json;
    }

    private CarDTO getCar(String id,String name,String brand,Integer year,BigDecimal price,String personId) {
        CarDTO carro = new CarDTO();
        carro.setId(id);
        carro.setName(name);
        carro.setBrand(brand);
        carro.setYear(year);
        carro.setPrice(price);
        carro.setPersonId(personId);
        return carro;
    }

    private PersonDTO getPerson(String id,String name,String cpf) {
        PersonDTO person = new PersonDTO();
        person.setId(id);
        person.setName(name);
        person.setCpf(cpf);
        return person;
    }

}
