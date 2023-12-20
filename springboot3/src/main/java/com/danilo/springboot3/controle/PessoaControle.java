package com.danilo.springboot3.controle;

import com.danilo.springboot3.dominio.Pessoa;
import com.danilo.springboot3.dto.PessoaDTO;
import com.danilo.springboot3.padrao_projeto.FacadeServico;
import jakarta.validation.Valid;
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
public class PessoaControle extends FacadeServico {

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody @Valid PessoaDTO pessoa) {
        this.pessoa.inserir(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> alterar(@RequestBody @Valid PessoaDTO pessoa) {
        this.pessoa.alterar(pessoa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable String id) {
        this.pessoa.remover(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> buscarTodos() {
        List<Pessoa> pessoas = this.pessoa.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscar(@PathVariable String id) {
        Pessoa pessoa = this.pessoa.buscar(id);
        return ResponseEntity.status(HttpStatus.OK).body(pessoa);
    }

}
