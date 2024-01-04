package com.danilo.springboot3;

import com.danilo.springboot3.domain.Person;
import com.danilo.springboot3.dto.PersonDTO;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonTest extends ObjectTest {

    private final String URL = "/pessoa";

    @Test
    @Transactional
    public void person_find_status200() throws Exception {
        String name = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PersonDTO dto = this.getPerson(null,name,cpf);
        this.repository.person.insert(dto);
        Person person = this.repository.person.get(null,cpf);
        String url = this.URL + "/" + person.getId();
        this.mock.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    public void person_find_status400() throws Exception {
        String id = "1";
        String url = this.URL + "/" + id;
        this.mock.perform(get(url)).andExpect(status().isBadRequest());
    }

    @Test
    public void person_insert_status201() throws Exception {
        String name = "Danilo Gonçalves";
        String cpf = "462.350.090-03";
        PersonDTO person = this.getPerson(null,name,cpf);
        String json = this.createJson(person).toString();
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void person_insert_status400() throws Exception {
        String name = "Danilo Gonçalves";
        String cpf = "111.111.11-11";
        PersonDTO person = this.getPerson(null,name,cpf);
        String json = this.createJson(person).toString();
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void person_insert_status409() throws Exception {
        String name = "Danilo Gonçalves";
        String cpf = "462.350.090-03";
        PersonDTO person = this.getPerson(null,name,cpf);
        this.repository.person.insert(person);
        String json = this.createJson(person).toString();
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isConflict());
    }

    @Test
    @Transactional
    public void person_update_status204() throws Exception {
        String name = "Danilo Gonçalves";
        String cpf = "462.350.090-03";
        PersonDTO dto = this.getPerson(null,name,cpf);
        this.repository.person.insert(dto);

        name = "Danilo Gonçalves Vicente";
        cpf = "462.350.090-03";
        Person person = this.repository.person.get(null,cpf);
        dto = this.getPerson(person.getId(),name,cpf);
        String json = this.createJson(dto).toString();
        this.mock.perform(put(this.URL).contentType(this.JSON).content(json)).andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void person_update_status400() throws Exception {
        String name = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PersonDTO dto = this.getPerson(null,name,cpf);
        this.repository.person.insert(dto);

        name = "";
        cpf = "462.350.090-03";
        Person person = this.repository.person.get(null,cpf);
        dto = this.getPerson(person.getId(),name,cpf);
        String json = this.createJson(dto).toString();
        this.mock.perform(put(this.URL).contentType(this.JSON).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void person_findAll_status204() throws Exception {
        this.mock.perform(get(this.URL)).andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void person_findAll_status200() throws Exception {
        String name = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PersonDTO person = this.getPerson(null,name,cpf);
        this.repository.person.insert(person);
        this.mock.perform(get(this.URL)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void person_delete_status204() throws Exception {
        String name = "Danilo Gonçalves Vicente";
        String cpf = "462.350.090-03";
        PersonDTO personDTO = this.getPerson(null,name,cpf);
        this.repository.person.insert(personDTO);
        Person person = this.repository.person.get(null,cpf);
        String url = this.URL + "/" + person.getId();
        this.mock.perform(delete(url)).andExpect(status().isNoContent());
    }

    @Test
    public void person_delete_status400() throws Exception {
        String id = "1";
        String url = this.URL + "/" + id;
        this.mock.perform(delete(url)).andExpect(status().isBadRequest());
    }

    private JSONObject createJson(PersonDTO person) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id",person.getId());
        json.put("name",person.getName());
        json.put("cpf",person.getCpf());
        return json;
    }

    private PersonDTO getPerson(String id,String name,String cpf) {
        PersonDTO person = new PersonDTO();
        person.setId(id);
        person.setName(name);
        person.setCpf(cpf);
        return person;
    }

}
