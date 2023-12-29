package com.danilo.springboot3.controller;

import com.danilo.springboot3.configuration.Jwt;
import com.danilo.springboot3.domain.User;
import com.danilo.springboot3.dto.AuthenticationDTO;
import com.danilo.springboot3.dto.UserDTO;
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
@RequestMapping(value = "/usuario")
public class UserController {

    @Autowired
    private FacadeService service;

    @Autowired
    private Jwt jwt;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid UserDTO user) {
        this.service.user.insert(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UserDTO user) {
        this.service.user.update(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable String id) {
        this.service.user.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = this.service.user.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable String id) {
        User user = this.service.user.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticationDTO auth) {
        this.service.user.validation(auth);
        String token = this.jwt.generateToken(auth.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
