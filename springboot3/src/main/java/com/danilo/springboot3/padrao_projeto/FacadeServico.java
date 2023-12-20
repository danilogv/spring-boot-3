package com.danilo.springboot3.padrao_projeto;

import com.danilo.springboot3.servico.CarroServico;
import com.danilo.springboot3.servico.PessoaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacadeServico {

    @Autowired
    protected PessoaServico pessoa;

    @Autowired
    protected CarroServico carro;

}
