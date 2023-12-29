package com.danilo.springboot3;

import com.danilo.springboot3.domain.User;
import com.danilo.springboot3.dto.UserDTO;
import com.danilo.springboot3.enumeration.Permission;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTest extends ObjectTest {

    private final String URL = "/usuario";

    @Test
    @Transactional
    public void user_find_status200() throws Exception {
        String username = "danilo";
        String password = "123";
        Permission permission = Permission.USER;
        UserDTO dto = this.getUser(null,username,password,permission);
        this.repository.user.insert(dto);
        User user = this.repository.user.get(dto);
        String url = this.URL + "/" + user.getId();
        this.mock.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void user_find_status400() throws Exception {
        String id = "1";
        String username = "danilo";
        String password = "123";
        Permission permission = Permission.USER;
        UserDTO dto = this.getUser(null,username,password,permission);
        this.repository.user.insert(dto);
        String url = this.URL + "/" + id;
        this.mock.perform(get(url)).andExpect(status().isBadRequest());
    }

    @Test
    public void user_insert_status201() throws Exception {
        String username = "danilo";
        String password = "123";
        Permission permission = Permission.ADMIN;
        UserDTO user = this.getUser(null,username,password,permission);
        String json = this.createJson(user).toString();
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void user_insert_status400() throws Exception {
        String username = "danilo";
        String password = "";
        Permission permission = Permission.ADMIN;
        UserDTO user = this.getUser(null,username,password,permission);
        String json = this.createJson(user).toString();
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void user_insert_status409() throws Exception {
        String username = "danilo";
        String password = "123";
        Permission permission = Permission.ADMIN;
        UserDTO user = this.getUser(null,username,password,permission);
        String json = this.createJson(user).toString();
        this.repository.user.insert(user);
        this.mock.perform(post(this.URL).contentType(this.JSON).content(json)).andExpect(status().isConflict());
    }

    @Test
    @Transactional
    public void user_update_status204() throws Exception {
        String username = "danilo";
        String password = "123";
        Permission permission = Permission.USER;
        UserDTO dto = this.getUser(null,username,password,permission);
        this.repository.user.insert(dto);

        username = "danilo";
        password = "456";
        permission = Permission.ADMIN;
        dto = this.getUser(null,username,password,permission);
        User user = this.repository.user.get(dto);
        dto = this.getUser(user.getId(),username,password,permission);
        String json = this.createJson(dto).toString();
        this.mock.perform(put(this.URL).contentType(this.JSON).content(json)).andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void user_update_status400() throws Exception {
        String username = "danilo";
        String password = "123";
        Permission permission = Permission.USER;
        UserDTO dto = this.getUser(null,username,password,permission);
        this.repository.user.insert(dto);

        User user = this.repository.user.get(dto);
        username = "";
        password = "123";
        dto = this.getUser(user.getId(),username,password,permission);
        String json = this.createJson(dto).toString();
        this.mock.perform(put(this.URL).contentType(this.JSON).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void user_findAll_status204() throws Exception {
        this.mock.perform(get(this.URL)).andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void user_findAll_status200() throws Exception {
        String username = "danilo";
        String password = "123";
        Permission permission = Permission.USER;
        UserDTO dto = this.getUser(null,username,password,permission);
        this.repository.user.insert(dto);
        this.mock.perform(get(this.URL)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void user_delete_status204() throws Exception {
        String username = "danilo";
        String password = "123";
        Permission permission = Permission.USER;
        UserDTO userDTO = this.getUser(null,username,password,permission);
        this.repository.user.insert(userDTO);
        User user = this.repository.user.get(userDTO);
        String url = this.URL + "/" + user.getId();
        this.mock.perform(delete(url)).andExpect(status().isNoContent());
    }

    @Test
    public void user_delete_status400() throws Exception {
        String id = "1";
        String url = this.URL + "/" + id;
        this.mock.perform(delete(url)).andExpect(status().isBadRequest());
    }

    private JSONObject createJson(UserDTO user) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id",user.getId());
        json.put("username",user.getUsername());
        json.put("password",user.getPassword());
        json.put("permission",user.getPermission());
        return json;
    }

    private UserDTO getUser(String id,String username,String password,Permission permission) {
        UserDTO user = new UserDTO();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setPermission(permission);
        return user;
    }

}
