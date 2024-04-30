package com.danilo.springboot3.design_pattern.singleton;

import com.danilo.springboot3.domain.Car;
import com.danilo.springboot3.domain.Person;
import com.danilo.springboot3.domain.User;
import com.danilo.springboot3.dto.CarDTO;
import com.danilo.springboot3.dto.PersonDTO;
import com.danilo.springboot3.dto.UserDTO;
import lombok.Getter;

public class Singleton {

    @Getter
    private static Car car = new Car();

    @Getter
    private static Person person = new Person();

    @Getter
    private static User user = new User();

    @Getter
    private static CarDTO carDTO = new CarDTO();

    @Getter
    private static PersonDTO personDTO = new PersonDTO();

    @Getter
    private static UserDTO userDTO = new UserDTO();

    public static void resetCar() {
        car = new Car();
    }

    public static void resetPerson() {
        person = new Person();
    }

    public static void resetUser() {
        user = new User();
    }

    public static void resetCarDTO() {
        carDTO = new CarDTO();
    }

    public static void resetPersonDTO() {
        personDTO = new PersonDTO();
    }

    public static void resetUserDTO() {
        userDTO = new UserDTO();
    }

}
