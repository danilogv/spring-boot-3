package com.danilo.springboot3.repositorio;

import com.danilo.springboot3.dominio.Carro;
import com.danilo.springboot3.dto.CarroDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarroRepositorio extends JpaRepository<Carro,String> {

    @Modifying
    @Query(value =
        "INSERT INTO carro(id,marca,nome,ano,valor,pessoa_id) " +
        "VALUES (uuid(),:#{#carro.marca},:#{#carro.nome},:#{#carro.ano},:#{#carro.valor},:#{#carro.idPessoa})"
    ,nativeQuery = true)
    void inserir(CarroDTO carro);

    @Modifying
    @Query(value =
        "UPDATE carro " +
        "SET marca = :#{#carro.marca},nome = :#{#carro.nome},ano = :#{#carro.ano},valor = :#{#carro.valor},pessoa_id = :#{#carro.idPessoa} " +
        "WHERE id = :#{#carro.id}"
    ,nativeQuery = true)
    void alterar(CarroDTO carro);

    @Modifying
    @Query(value =
        "DELETE FROM carro " +
        "WHERE id = :id"
    ,nativeQuery = true)
    void remover(String id);

    @Query(value =
        "SELECT * " +
        "FROM carro " +
        "ORDER BY nome"
    ,nativeQuery = true)
    List<Carro> buscarTodos();

    @Query(value =
        "SELECT * " +
        "FROM carro " +
        "WHERE id = :id"
    ,nativeQuery = true)
    Carro buscar(String id);

    @Query(value =
        "SELECT CASE WHEN COUNT(id) > 0 THEN 'true' ELSE 'false' END " +
        "FROM carro " +
        "WHERE id = :#{#carro.id} " +
        "OR (nome = :#{#carro.nome} AND ano = :#{#carro.ano} AND pessoa_id = :#{#carro.idPessoa})"
    ,nativeQuery = true)
    Boolean existe(CarroDTO carro);

}
