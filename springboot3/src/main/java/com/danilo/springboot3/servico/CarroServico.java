package com.danilo.springboot3.servico;

import com.danilo.springboot3.dominio.Carro;
import com.danilo.springboot3.dto.CarroDTO;
import com.danilo.springboot3.padrao_projeto.FacadeRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class CarroServico {

    @Autowired
    private FacadeRepositorio repositorio;

    @Transactional
    public void inserir(CarroDTO carro) {
        CarroDTO dto = new CarroDTO();
        BeanUtils.copyProperties(carro,dto);
        dto.setId(null);

        if (this.repositorio.carro.existe(dto)) {
            String msg = "Carro já cadastrado para essa pessoa na base de dados.";
            throw new ResponseStatusException(HttpStatus.CONFLICT,msg);
        }

        this.repositorio.carro.inserir(carro);
    }

    @Transactional
    public void alterar(CarroDTO carro) {
        if (!this.repositorio.carro.existe(carro)) {
            String msg = "Carro não cadastrado na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        this.repositorio.carro.alterar(carro);
    }

    @Transactional
    public void remover(String id) {
        CarroDTO carro = new CarroDTO();
        carro.setId(id);

        if (!this.repositorio.carro.existe(carro)) {
            String msg = "Carro não cadastrado na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        this.repositorio.carro.remover(id);
    }

    public List<Carro> buscarTodos() {
        List<Carro> carros = this.repositorio.carro.buscarTodos();

        if (carros.isEmpty()) {
            String msg = "Não há carros cadastrados na base de dados.";
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,msg);
        }

        return carros;
    }

    public Carro buscar(String id) {
        Carro carro = this.repositorio.carro.buscar(id,null,null,null);

        if (Objects.isNull(carro)) {
            String msg = "Carro não cadastrado na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        return carro;
    }

}
