package com.danilo.springboot3.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "carro")
@Getter
@Setter
@ToString(exclude = {"pessoa"})
@EqualsAndHashCode(of = {"marca","nome","pessoa"})
public class Carro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "marca")
    private String marca;

    @Column(name = "nome")
    private String nome;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "valor")
    private BigDecimal valor;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Pessoa pessoa;

}
