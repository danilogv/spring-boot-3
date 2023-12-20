package com.danilo.springboot3.repositorio;

import com.danilo.springboot3.dominio.Pessoa;
import com.danilo.springboot3.dto.PessoaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaRepositorio extends JpaRepository<Pessoa,String> {

    @Modifying
    @Query(value =
        "INSERT INTO pessoa(id,nome,cpf) " +
        "VALUES (uuid(),:#{#pessoa.nome},:#{#pessoa.cpf})"
    ,nativeQuery = true)
    void inserir(PessoaDTO pessoa);

    @Modifying
    @Query(value =
        "UPDATE pessoa " +
        "SET nome = :#{#pessoa.nome},cpf = :#{#pessoa.cpf} " +
        "WHERE id = :#{#pessoa.id}"
    ,nativeQuery = true)
    void alterar(PessoaDTO pessoa);

    @Modifying
    @Query(value =
        "DELETE FROM pessoa " +
        "WHERE id = :id"
    ,nativeQuery = true)
    void remover(String id);

    @Query(value =
        "SELECT * " +
        "FROM pessoa " +
        "ORDER BY nome"
    ,nativeQuery = true)
    List<Pessoa> buscarTodos();

    @Query(value =
        "SELECT * " +
        "FROM pessoa " +
        "WHERE id = :id " +
        "OR cpf = :cpf"
    ,nativeQuery = true)
    Pessoa buscar(String id,String cpf);

    @Query(value =
        "SELECT CASE WHEN COUNT(id) > 0 THEN 'true' ELSE 'false' END " +
        "FROM pessoa " +
        "WHERE id = :#{#pessoa.id} " +
        "OR cpf = :#{#pessoa.cpf}"
    ,nativeQuery = true)
    Boolean existe(PessoaDTO pessoa);

}
