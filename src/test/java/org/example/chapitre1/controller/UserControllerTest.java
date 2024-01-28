package org.example.chapitre1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.dto.UserRequest;
import org.example.chapitre1.entity.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "user", authorities = {"USER"})
class UserControllerTest {

    private static final String API_GET_AND_DELETE_USER_BY_ID = "/api/v1/users/{id}";
    private static final String API_CREATE_AND_GET_ALL_USERS = "/api/v1/users";
    private static final String API_DYNAMIC_SEARCH_WITH_STREAM = "/api/v1/users/search-stream";
    private static final String API_DYNAMIC_SEARCH_WITH_JDBC = "/api/v1/users/search-jdbc";

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

    /*************************************************** Dynamic search stream *****************************************************************/
    @Test
    public void should_return_ok_when_find_dynamic_users_and_users_list_is_not_empty() throws Exception {
        mockMvc.perform(post(API_DYNAMIC_SEARCH_WITH_STREAM)
                        .content(new ObjectMapper().writeValueAsString(getExistingUserRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void should_return_ok_when_find_dynamic_users_and_users_list_is_empty() throws Exception {
        mockMvc.perform(post(API_DYNAMIC_SEARCH_WITH_STREAM)
                        .content(new ObjectMapper().writeValueAsString(getNotExistingUserRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    /*************************************************** Dynamic search jdbc *****************************************************************/
    @Test
    public void should_return_ok_when_find_dynamic_users_and_users_list_is_not_empty_with_jdbc_method() throws Exception {
        mockMvc.perform(post(API_DYNAMIC_SEARCH_WITH_JDBC)
                        .content(new ObjectMapper().writeValueAsString(getExistingUserRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void should_return_ok_when_find_dynamic_users_and_users_list_is_empty_with_jdbc_method() throws Exception {
        mockMvc.perform(post(API_DYNAMIC_SEARCH_WITH_JDBC)
                        .content(new ObjectMapper().writeValueAsString(getNotExistingUserRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    private UserRequest getExistingUserRequest() {
        return UserRequest.builder()
                .firstName("wael")
                .lastName("amara")
                .email("wael.amara@example.com")
                .build();
    }

    private UserRequest getNotExistingUserRequest() {
        return UserRequest.builder()
                .firstName("not_existing")
                .lastName("not_existing")
                .email("not_existing")
                .build();
    }


}