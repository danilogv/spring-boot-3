package com.danilo.springboot3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class PessoaDTO implements Serializable {

    private String id;

    @NotNull(message = "Informe o NOME.")
    @NotBlank(message = "Informe o NOME.")
    @Size(max = 100,message = "NOME só pode conter no máximo 100 caracteres.")
    private String nome;

    @NotNull(message = "Informe o CPF.")
    @NotBlank(message = "Informe o CPF.")
    @Size(min = 14,max = 14,message = "CPF inválido.")
    private String cpf;

}
