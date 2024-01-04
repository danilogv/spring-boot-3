package com.danilo.springboot3.controller;

import com.danilo.springboot3.domain.Person;
import com.danilo.springboot3.dto.PersonDTO;
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
@RequestMapping(value = "/pessoa")
public class PersonController {

    @Autowired
    private FacadeService service;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid PersonDTO person) {
        this.service.person.insert(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid PersonDTO person) {
        this.service.person.update(person);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable String id) {
        this.service.person.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAll() {
        List<Person> people = this.service.person.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(people);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> get(@PathVariable String id) {
        Person person = this.service.person.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

}
