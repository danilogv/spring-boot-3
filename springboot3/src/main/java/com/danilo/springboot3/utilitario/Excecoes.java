package com.danilo.springboot3.utilitario;

import com.danilo.springboot3.dto.ErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@ControllerAdvice
public class Excecoes {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> erroArgumentoInvalido(MethodArgumentNotValidException ex) {
        if (Objects.nonNull(ex.getFieldError())) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String msg = ex.getFieldError().getDefaultMessage();
            ErroDTO erro = new ErroDTO(status.value(),msg);
            return new ResponseEntity<>(erro,status);
        }
        else {
            ErroDTO erro = this.erroGenerico();
            return new ResponseEntity<>(erro,HttpStatusCode.valueOf(erro.getStatus()));
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroDTO> erroRegraNegocio(ResponseStatusException ex) {
        HttpStatusCode status = ex.getStatusCode();
        String msg = ex.getReason();
        ErroDTO erro = new ErroDTO(status.value(),msg);
        return new ResponseEntity<>(erro,status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> erroServidor(Exception ex) {
        ErroDTO erro = this.erroGenerico();
        return new ResponseEntity<>(erro,HttpStatusCode.valueOf(erro.getStatus()));
    }

    private ErroDTO erroGenerico() {
        Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String msg = "Erro de servidor.";
        ErroDTO erro = new ErroDTO(status,msg);
        return erro;
    }

}
