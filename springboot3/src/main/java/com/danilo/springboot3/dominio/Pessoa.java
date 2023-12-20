package com.danilo.springboot3.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@ToString(exclude = "carros")
@EqualsAndHashCode(of = {"cpf"})
public class Pessoa implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY)
    private List<Carro> carros;

}
