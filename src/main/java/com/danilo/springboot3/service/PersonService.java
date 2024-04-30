package com.danilo.springboot3.service;

import com.danilo.springboot3.design_pattern.singleton.Singleton;
import com.danilo.springboot3.domain.Person;
import com.danilo.springboot3.dto.PersonDTO;
import com.danilo.springboot3.design_pattern.facade.FacadeRepository;
import com.danilo.springboot3.utility.Functions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class PersonService {

    @Autowired
    private FacadeRepository repository;

    @Transactional
    public void insert(PersonDTO person) {
        if (!Functions.cpfIsValid(person.getCpf())) {
            String msg = "CPF inválido.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        if (this.repository.person.exists(person)) {
            String msg = "Pessoa já cadastrada na base de dados.";
            throw new ResponseStatusException(HttpStatus.CONFLICT,msg);
        }

        this.repository.person.insert(person);
    }

    @Transactional
    public void update(PersonDTO person) {
        if (!Functions.cpfIsValid(person.getCpf())) {
            String msg = "CPF inválido.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        if (!this.repository.person.exists(person)) {
            String msg = "Pessoa não cadastrada na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        this.repository.person.update(person);
    }

    @Transactional
    public void remove(String id) {
        PersonDTO person = Singleton.getPersonDTO();
        person.setId(id);

        if (!this.repository.person.exists(person)) {
            String msg = "Pessoa não cadastrada na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        this.repository.person.remove(id);
    }

    public List<Person> getAll() {
        List<Person> people = this.repository.person.getAll();

        if (people.isEmpty()) {
            String msg = "Não há pessoas cadastradas na base de dados.";
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,msg);
        }

        return people;
    }

    public Person get(String id) {
        Person person = this.repository.person.get(id,null);

        if (Objects.isNull(person)) {
            String msg = "Pessoa não cadastrada na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        return person;
    }

}
