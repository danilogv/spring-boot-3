package com.danilo.springboot3.design_pattern;

import com.danilo.springboot3.service.CarService;
import com.danilo.springboot3.service.PersonService;
import com.danilo.springboot3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacadeService {

    @Autowired
    public PersonService person;

    @Autowired
    public CarService car;

    @Autowired
    public UserService user;

}
