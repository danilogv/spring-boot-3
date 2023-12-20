package com.danilo.springboot3.controle;

import com.danilo.springboot3.dominio.Carro;
import com.danilo.springboot3.dto.CarroDTO;
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
@RequestMapping(value = "/carro")
public class CarroControle extends FacadeServico {

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody @Valid CarroDTO carro) {
        this.carro.inserir(carro);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> alterar(@RequestBody @Valid CarroDTO carro) {
        this.carro.alterar(carro);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable String id) {
        this.carro.remover(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<Carro>> buscarTodos() {
        List<Carro> carros = this.carro.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(carros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> buscar(@PathVariable String id) {
        Carro carro = this.carro.buscar(id);
        return ResponseEntity.status(HttpStatus.OK).body(carro);
    }

}
