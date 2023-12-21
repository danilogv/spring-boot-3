package com.danilo.springboot3;

import com.danilo.springboot3.dominio.Carro;
import com.danilo.springboot3.dominio.Pessoa;
import com.danilo.springboot3.dto.CarroDTO;
import com.danilo.springboot3.dto.PessoaDTO;
import com.danilo.springboot3.padrao_projeto.FacadeRepositorio;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = Principal.class)
public class CarroTeste {

    @Autowired
    private FacadeRepositorio repositorio;

    @Autowired
    private MockMvc mock;

    private final MediaType JSON = MediaType.APPLICATION_JSON;

    private final String URL_CARRO = "/carro";

    private final String URL_PESSOA = "/pessoa";

    @Test
    public void carro_buscarTodos_status204() throws Exception {
        this.mock.perform(get(this.URL_CARRO)).andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void carro_buscarTodos_status200() throws Exception {
        String nome = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PessoaDTO pessoaDTO = this.obtemPessoa(null,nome,cpf);
        this.repositorio.pessoa.inserir(pessoaDTO);
        Pessoa pessoa = this.repositorio.pessoa.buscar(null,cpf);

        String marca = "Chevrolet";
        nome = "Celta";
        Integer ano = 2008;
        BigDecimal valor = new BigDecimal("14500.00");
        String idPessoa = pessoa.getId();
        CarroDTO carroDTO = this.obtemCarro(null,marca,nome,ano,valor,idPessoa);
        this.repositorio.carro.inserir(carroDTO);
        this.mock.perform(get(this.URL_CARRO)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void carro_buscar_status200() throws Exception {
        String nome = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PessoaDTO pessoaDTO = this.obtemPessoa(null,nome,cpf);
        this.repositorio.pessoa.inserir(pessoaDTO);
        Pessoa pessoa = this.repositorio.pessoa.buscar(null,cpf);

        String marca = "Chevrolet";
        nome = "Celta";
        Integer ano = 2008;
        BigDecimal valor = new BigDecimal("14500.00");
        String idPessoa = pessoa.getId();
        CarroDTO carroDTO = this.obtemCarro(null,marca,nome,ano,valor,idPessoa);
        this.repositorio.carro.inserir(carroDTO);
        Carro carro = this.repositorio.carro.buscar(null,nome,ano,idPessoa);
        String url = this.URL_CARRO + "/" + carro.getId();
        this.mock.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void carro_buscar_status400() throws Exception {
        String id = "63e51b05-2b8b-4a5b-925c-e75f15ec2222";
        String url = this.URL_CARRO + "/" + id;
        this.mock.perform(get(url)).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void carro_insercao_status201() throws Exception {
        String nome = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PessoaDTO pessoaDTO = this.obtemPessoa(null,nome,cpf);
        this.repositorio.pessoa.inserir(pessoaDTO);
        Pessoa pessoa = this.repositorio.pessoa.buscar(null,cpf);

        String marca = "Chevrolet";
        nome = "Celta";
        Integer ano = 2008;
        BigDecimal valor = new BigDecimal("14500.00");
        String idPessoa = pessoa.getId();
        CarroDTO carroDTO = this.obtemCarro(null,marca,nome,ano,valor,idPessoa);
        String json = this.criaJson(carroDTO).toString();
        this.mock.perform(post(this.URL_CARRO).contentType(this.JSON).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void carro_insercao_status400() throws Exception {
        String marca = "Chevrolet";
        String nome = "Celta";
        Integer ano = 2008;
        BigDecimal valor = BigDecimal.ZERO;
        String idPessoa = "63e51b05-2b8b-4a5b-925c-e75f15ec2222";
        CarroDTO carroDTO = this.obtemCarro(null,marca,nome,ano,valor,idPessoa);
        String json = this.criaJson(carroDTO).toString();
        this.mock.perform(post(this.URL_CARRO).contentType(this.JSON).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void carro_insercao_status409() throws Exception {
        String nome = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PessoaDTO pessoaDTO = this.obtemPessoa(null,nome,cpf);
        this.repositorio.pessoa.inserir(pessoaDTO);
        Pessoa pessoa = this.repositorio.pessoa.buscar(null,cpf);

        String marca = "Chevrolet";
        nome = "Celta";
        Integer ano = 2008;
        BigDecimal valor = new BigDecimal("14500.00");
        String idPessoa = pessoa.getId();
        CarroDTO carroDTO = this.obtemCarro(null,marca,nome,ano,valor,idPessoa);
        this.repositorio.carro.inserir(carroDTO);

        marca = "Chevrolet";
        nome = "Celta";
        ano = 2008;
        valor = new BigDecimal("20000.00");
        idPessoa = pessoa.getId();
        carroDTO = this.obtemCarro(null,marca,nome,ano,valor,idPessoa);
        String json = this.criaJson(carroDTO).toString();
        this.mock.perform(post(this.URL_CARRO).contentType(this.JSON).content(json)).andExpect(status().isConflict());
    }

    @Test
    @Transactional
    public void carro_alteracao_status204() throws Exception {
        String nome = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PessoaDTO pessoaDTO = this.obtemPessoa(null,nome,cpf);
        this.repositorio.pessoa.inserir(pessoaDTO);
        Pessoa pessoa = this.repositorio.pessoa.buscar(null,cpf);

        String marca = "Chevrolet";
        nome = "Celta";
        Integer ano = 2008;
        BigDecimal valor = new BigDecimal("14500.00");
        String idPessoa = pessoa.getId();
        CarroDTO carroDTO = this.obtemCarro(null,marca,nome,ano,valor,idPessoa);
        this.repositorio.carro.inserir(carroDTO);
        Carro carro = this.repositorio.carro.buscar(null,nome,ano,idPessoa);

        String idCarro = carro.getId();
        marca = "Chevrolet";
        nome = "Celta";
        ano = 2013;
        valor = new BigDecimal("20000.00");
        idPessoa = pessoa.getId();
        carroDTO = this.obtemCarro(idCarro,marca,nome,ano,valor,idPessoa);
        String json = this.criaJson(carroDTO).toString();
        String url = this.URL_CARRO + "/" + carro.getId();
        this.mock.perform(put(url).contentType(this.JSON).content(json)).andExpect(status().isNoContent());
    }

    @Test
    public void carro_alteracao_status400() throws Exception {
        String idCarro = "1";
        String marca = "Chevrolet";
        String nome = "Celta";
        Integer ano = 2013;
        BigDecimal valor = new BigDecimal("20000.00");
        String idPessoa = "1";
        CarroDTO carro = this.obtemCarro(idCarro,marca,nome,ano,valor,idPessoa);
        String json = this.criaJson(carro).toString();
        String url = this.URL_CARRO + "/" + idCarro;
        this.mock.perform(put(url).contentType(this.JSON).content(json)).andExpect(status().isBadRequest());
    }

    private JSONObject criaJson(CarroDTO carro) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id",carro.getId());
        json.put("marca",carro.getMarca());
        json.put("nome",carro.getNome());
        json.put("ano",carro.getAno());
        json.put("valor",carro.getValor());
        json.put("idPessoa",carro.getIdPessoa());
        return json;
    }

    private JSONObject criaJson(PessoaDTO pessoa) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id",pessoa.getId());
        json.put("nome",pessoa.getNome());
        json.put("cpf",pessoa.getCpf());
        return json;
    }

    private CarroDTO obtemCarro(String id,String marca,String nome,Integer ano,BigDecimal valor,String idPessoa) {
        CarroDTO carro = new CarroDTO();
        carro.setId(id);
        carro.setMarca(marca);
        carro.setNome(nome);
        carro.setAno(ano);
        carro.setValor(valor);
        carro.setIdPessoa(idPessoa);
        return carro;
    }

    private PessoaDTO obtemPessoa(String id,String nome,String cpf) {
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setId(id);
        pessoa.setNome(nome);
        pessoa.setCpf(cpf);
        return pessoa;
    }

}
