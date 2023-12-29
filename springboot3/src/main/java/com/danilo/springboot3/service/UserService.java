package com.danilo.springboot3.service;

import com.danilo.springboot3.domain.User;
import com.danilo.springboot3.dto.AuthenticationDTO;
import com.danilo.springboot3.dto.UserDTO;
import com.danilo.springboot3.design_pattern.FacadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private FacadeRepository repository;

    @Transactional
    public void insert(UserDTO user) {
        if (this.repository.user.exists(user)) {
            String msg = "Usuário já cadastrado na base de dados.";
            throw new ResponseStatusException(HttpStatus.CONFLICT,msg);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.repository.user.insert(user);
    }

    @Transactional
    public void update(UserDTO user) {
        if (!this.repository.user.exists(user)) {
            String msg = "Usuário não cadastrado na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        this.repository.user.update(user);
    }

    @Transactional
    public void remove(String id) {
        UserDTO user = new UserDTO();
        user.setId(id);

        if (!this.repository.user.exists(user)) {
            String msg = "Usuário não cadastrado na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        this.repository.user.remove(id);
    }

    public List<User> getAll() {
        List<User> users = this.repository.user.getAll();

        if (users.isEmpty()) {
            String msg = "Não há usuários cadastradas na base de dados.";
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,msg);
        }

        return users;
    }

    public User get(String id) {
        UserDTO dto = new UserDTO();
        dto.setId(id);

        User user = this.repository.user.get(dto);

        if (Objects.isNull(user)) {
            String msg = "Pessoa não cadastrada na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        return user;
    }

    public void validation(AuthenticationDTO authentication) {
        UserDetails usuario = this.loadUserByUsername(authentication.getUsername());
        BCryptPasswordEncoder decodificacao = new BCryptPasswordEncoder();

        if (!decodificacao.matches(authentication.getPassword(),usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Usuário inválido.");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDTO dto = new UserDTO();
        dto.setUsername(username);
        User user = this.repository.user.get(dto);

        if (Objects.isNull(user)) {
            String msg = "Usuário não encontrado";
            throw new UsernameNotFoundException(msg);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                user.getAuthorities()
        );

    }

}
