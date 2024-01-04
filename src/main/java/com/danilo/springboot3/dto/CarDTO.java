package com.danilo.springboot3.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CarDTO implements Serializable {

    private String id;

    @NotNull(message = "Informe o NOME.")
    @NotBlank(message = "Informe o NOME.")
    @Size(max = 100,message = "NOME só pode conter no máximo 100 caracteres.")
    private String name;

    @NotNull(message = "Informe o MARCA.")
    @NotBlank(message = "Informe o MARCA.")
    @Size(max = 100,message = "MARCA só pode conter no máximo 100 caracteres.")
    private String brand;

    @NotNull(message = "Informe o ANO.")
    @Min(value = 1970,message = "Idade mínima do carro é de 1970.")
    @Max(value = 2024,message = "Idade máxima do carro é de 2024.")
    private Integer year;

    @NotNull(message = "Informe o VALOR.")
    @Min(value = 1,message = "VALOR do carro não pode ser zerado.")
    private BigDecimal price;

    @NotNull(message = "Informe a PESSOA.")
    @NotBlank(message = "Informe o PESSOA.")
    @Size(min = 36,max = 36,message = "ID da Pessoa inválido.")
    private String personId;

}
