package com.danilo.springboot3.design_pattern.facade;

import com.danilo.springboot3.repository.CarRepository;
import com.danilo.springboot3.repository.PersonRepository;
import com.danilo.springboot3.repository.RoleRepository;
import com.danilo.springboot3.repository.UserRepository;
import com.danilo.springboot3.repository.UserRoleRepository;
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

    @Autowired
    public RoleRepository role;

    @Autowired
    public UserRoleRepository userRole;

}
