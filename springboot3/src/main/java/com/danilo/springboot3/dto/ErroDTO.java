package com.danilo.springboot3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErroDTO implements Serializable {

    private Integer status;
    private String mensagem;

}
