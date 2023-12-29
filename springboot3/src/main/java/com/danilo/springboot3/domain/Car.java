package com.danilo.springboot3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Table(name = "car")
@Getter
@Setter
@ToString(exclude = {"person"})
@EqualsAndHashCode(of = {"name","brand","person"})
public class Car implements Serializable {

    @Id
    @Column(name = "id",nullable = false,unique = true)
    private String id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "brand",nullable = false)
    private String brand;

    @Column(name = "year_fabrication",nullable = false)
    private Integer year;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Person person;

}
