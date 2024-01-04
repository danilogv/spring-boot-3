package com.danilo.springboot3.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserRoleDTO implements Serializable {

    private String userId;
    private String roleId;

}
