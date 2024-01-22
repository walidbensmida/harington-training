package org.example.chapitre1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.example.chapitre1.dto.AccountDto;
import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.entity.RoleEnum;
import org.example.chapitre1.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "user", authorities = {"USER"})
public class AccountControllerTest {

    private static final String API_GET_AND_DELETE_ACCOUNT_BY_ID = "/api/v1/accounts/{id}";
    private static final String API_CREATE_AND_GET_ALL_ACCOUNT = "/api/v1/accounts";

    @Inject
    UserService userService;
    @Inject
    protected MockMvc mockMvc;

    /*************************************************** Get account by id *****************************************************************/

    @Test
    public void should_return_ok_when_find_account_by_id_and_account_exist() throws Exception {
        mockMvc.perform(get(API_GET_AND_DELETE_ACCOUNT_BY_ID, 102)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_not_found_when_find_account_by_id_and_account_does_not_exist() throws Exception {
        mockMvc.perform(get(API_GET_AND_DELETE_ACCOUNT_BY_ID, 7000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /*************************************************** Create new account *****************************************************************/
    @Test
    public void should_return_ok_when_create_new_account() throws Exception {
        UserDto userDto = UserDto.builder().firstName("test").lastName("test").email("test@yopmail.com").password("1234").role(RoleEnum.CLIENT).build();
        UserDto saveUser = userService.save(userDto);
        AccountDto accountDto = AccountDto.builder().userId(saveUser.getId()).balance(1000.0F).build();
        mockMvc.perform(post(API_CREATE_AND_GET_ALL_ACCOUNT)
                        .content(new ObjectMapper().writeValueAsString(accountDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    /*************************************************** Get all accounts *****************************************************************/
    @Test
    public void should_return_ok_when_find_all_accounts() throws Exception {
        mockMvc.perform(get(API_CREATE_AND_GET_ALL_ACCOUNT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    /*************************************************** delete account *****************************************************************/
    @Test
    public void should_return_ok_when_delete_account_by_id() throws Exception {
        mockMvc.perform(delete(API_GET_AND_DELETE_ACCOUNT_BY_ID, 103)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_not_found_when_delete_account_by_id_and_account_does_not_exist() throws Exception {
        mockMvc.perform(delete(API_GET_AND_DELETE_ACCOUNT_BY_ID, 5000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
