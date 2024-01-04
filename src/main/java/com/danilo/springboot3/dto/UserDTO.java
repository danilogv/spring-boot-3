package com.danilo.springboot3.dto;

import com.danilo.springboot3.enumeration.Permission;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class UserDTO implements Serializable {

    private String id;

    @NotNull(message = "Informe o LOGIN.")
    @NotBlank(message = "Informe o LOGIN.")
    @Size(max = 15,message = "LOGIN só pode conter no máximo 15 caracteres.")
    private String username;

    @NotNull(message = "Informe a SENHA.")
    @NotBlank(message = "Informe a SENHA.")
    @Size(max = 255,message = "SENHA só pode conter no máximo 255 caracteres.")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Informe a PERMISSÃO de usuário.")
    private Permission role;

}
