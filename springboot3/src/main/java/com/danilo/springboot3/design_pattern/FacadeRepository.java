package com.danilo.springboot3.design_pattern;

import com.danilo.springboot3.repository.CarRepository;
import com.danilo.springboot3.repository.PersonRepository;
import com.danilo.springboot3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacadeRepository {

    @Autowired
    public PersonRepository person;

    @Autowired
    public CarRepository car;

    @Autowired
    public UserRepository user;

}
