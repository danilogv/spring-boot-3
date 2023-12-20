package com.danilo.springboot3.servico;

import com.danilo.springboot3.dominio.Pessoa;
import com.danilo.springboot3.dto.PessoaDTO;
import com.danilo.springboot3.padrao_projeto.FacadeRepositorio;
import com.danilo.springboot3.utilitario.Funcoes;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class PessoaServico extends FacadeRepositorio {

    @Transactional
    public void inserir(PessoaDTO pessoa) {
        if (!Funcoes.validouCpf(pessoa.getCpf())) {
            String msg = "CPF inválido.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        if (this.pessoa.existe(pessoa)) {
            String msg = "Pessoa já cadastrada na base de dados.";
            throw new ResponseStatusException(HttpStatus.CONFLICT,msg);
        }

        this.pessoa.inserir(pessoa);
    }

    @Transactional
    public void alterar(PessoaDTO pessoa) {
        if (!Funcoes.validouCpf(pessoa.getCpf())) {
            String msg = "CPF inválido.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        if (!this.pessoa.existe(pessoa)) {
            String msg = "Pessoa não cadastrada na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        this.pessoa.alterar(pessoa);
    }

    @Transactional
    public void remover(String id) {
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setId(id);

        if (!this.pessoa.existe(pessoa)) {
            String msg = "Pessoa não cadastrada na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        this.pessoa.remover(id);
    }

    public List<Pessoa> buscarTodos() {
        List<Pessoa> pessoas = this.pessoa.buscarTodos();

        if (pessoas.isEmpty()) {
            String msg = "Não há pessoas cadastradas na base de dados.";
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,msg);
        }

        return pessoas;
    }

    public Pessoa buscar(String id) {
        Pessoa pessoa = this.pessoa.buscar(id,null);

        if (Objects.isNull(pessoa)) {
            String msg = "Pessoa não cadastrada na base de dados.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        return pessoa;
    }

}
