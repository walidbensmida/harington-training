package org.example.chapitre1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.example.chapitre1.dto.AccountDto;
import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.entity.RoleEnum;
import org.example.chapitre1.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "user", authorities = {"USER"})
class UserControllerTest {

    private static final String API_GET_AND_DELETE_USER_BY_ID = "/api/v1/users/{id}";
    private static final String API_CREATE_AND_GET_ALL_USERS = "/api/v1/users";

    @Inject
    protected MockMvc mockMvc;

    /*************************************************** Get user by id *****************************************************************/

    @Test
    public void should_return_ok_when_find_user_by_id_and_user_exist() throws Exception {
        mockMvc.perform(get(API_GET_AND_DELETE_USER_BY_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_not_found_when_find_user_by_id_and_user_does_not_exist() throws Exception {
        mockMvc.perform(get(API_GET_AND_DELETE_USER_BY_ID, 7000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /*************************************************** Create new user *****************************************************************/
    @Test
    public void should_return_ok_when_create_new_user() throws Exception {
        UserDto userDto = UserDto.builder().firstName("test").lastName("test").email("test@yopmail.com").password("1234").role(RoleEnum.CLIENT).build();
        mockMvc.perform(post(API_CREATE_AND_GET_ALL_USERS)
                        .content(new ObjectMapper().writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    /*************************************************** Get all users *****************************************************************/
    @Test
    public void should_return_ok_when_find_all_users() throws Exception {
        mockMvc.perform(get(API_CREATE_AND_GET_ALL_USERS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    /*************************************************** delete users *****************************************************************/
    @Test
    public void should_return_ok_when_delete_user_by_id() throws Exception {
        mockMvc.perform(delete(API_GET_AND_DELETE_USER_BY_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_not_found_when_delete_user_by_id_and_user_does_not_exist() throws Exception {
        mockMvc.perform(delete(API_GET_AND_DELETE_USER_BY_ID, 5000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}