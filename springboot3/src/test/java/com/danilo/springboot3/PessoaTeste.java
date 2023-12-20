package com.danilo.springboot3;

import com.danilo.springboot3.dominio.Pessoa;
import com.danilo.springboot3.dto.PessoaDTO;
import com.danilo.springboot3.repositorio.PessoaRepositorio;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
public class PessoaTeste {

    @Autowired
    private PessoaRepositorio repositorio;

    @Autowired
    private MockMvc mock;

    private final MediaType JSON = MediaType.APPLICATION_JSON;

    private final String URL = "/pessoa";

    @Test
    public void pessoa_buscarTodos_status204() throws Exception {
        this.mock.perform(get(this.URL)).andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void pessoa_buscarTodos_status200() throws Exception {
        String nome = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PessoaDTO pessoa = this.obtemPessoa(null,nome,cpf);
        this.repositorio.inserir(pessoa);
        this.mock.perform(get(this.URL)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void pessoa_buscar_status200() throws Exception {
        String nome = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PessoaDTO dto = this.obtemPessoa(null,nome,cpf);
        this.repositorio.inserir(dto);
        Pessoa pessoa = this.repositorio.buscar(null,cpf);
        String url = this.URL + "/" + pessoa.getId();
        this.mock.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void pessoa_buscar_status400() throws Exception {
        String nome = "Danilo Gonçalves";
        String cpf = "462.350.090-03";
        PessoaDTO dto = this.obtemPessoa(null,nome,cpf);
        this.repositorio.inserir(dto);
        String url = this.URL + "/cffa87f1-bada-4c7d-b390-87e4a02a6acf";
        this.mock.perform(get(url)).andExpect(status().isBadRequest());
    }

    @Test
    public void pessoa_insercao_status201() throws Exception {
        String nome = "Danilo Gonçalves";
        String cpf = "462.350.090-03";
        PessoaDTO pessoa = this.obtemPessoa(null,nome,cpf);
        String json = this.criaJson(pessoa).toString();
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void pessoa_insercao_status400() throws Exception {
        String nome = "Danilo Gonçalves";
        String cpf = "111.111.11-11";
        PessoaDTO pessoa = this.obtemPessoa(null,nome,cpf);
        String json = this.criaJson(pessoa).toString();
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void pessoa_insercao_status409() throws Exception {
        String nome = "Danilo Gonçalves";
        String cpf = "462.350.090-03";
        PessoaDTO pessoa = this.obtemPessoa(null,nome,cpf);
        String json = this.criaJson(pessoa).toString();
        this.repositorio.inserir(pessoa);
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isConflict());
    }

    @Test
    @Transactional
    public void pessoa_alteracao_status204() throws Exception {
        String nome = "Danilo Gonçalves";
        String cpf = "462.350.090-03";
        PessoaDTO dto = this.obtemPessoa(null,nome,cpf);
        this.repositorio.inserir(dto);

        nome = "Danilo Gonçalves Vicente";
        cpf = "462.350.090-03";
        Pessoa pessoa = this.repositorio.buscar(null,cpf);
        dto = this.obtemPessoa(pessoa.getId(),nome,cpf);
        String json = this.criaJson(dto).toString();
        this.mock.perform(put(this.URL).contentType(this.JSON).content(json)).andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void pessoa_alteracao_status400() throws Exception {
        String nome = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PessoaDTO dto = this.obtemPessoa(null,nome,cpf);
        this.repositorio.inserir(dto);

        nome = "";
        cpf = "462.350.090-03";
        Pessoa pessoa = this.repositorio.buscar(null,cpf);
        dto = this.obtemPessoa(pessoa.getId(),nome,cpf);
        String json = this.criaJson(dto).toString();
        this.mock.perform(put(this.URL).contentType(this.JSON).content(json)).andExpect(status().isBadRequest());
    }

    private JSONObject criaJson(PessoaDTO pessoa) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id",pessoa.getId());
        json.put("nome",pessoa.getNome());
        json.put("cpf",pessoa.getCpf());
        return json;
    }

    private PessoaDTO obtemPessoa(String id,String nome,String cpf) {
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setId(id);
        pessoa.setNome(nome);
        pessoa.setCpf(cpf);
        return pessoa;
    }

}
