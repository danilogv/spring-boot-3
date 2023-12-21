package com.danilo.springboot3.padrao_projeto;

import com.danilo.springboot3.repositorio.CarroRepositorio;
import com.danilo.springboot3.repositorio.PessoaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacadeRepositorio {

    @Autowired
    public PessoaRepositorio pessoa;

    @Autowired
    public CarroRepositorio carro;

}
