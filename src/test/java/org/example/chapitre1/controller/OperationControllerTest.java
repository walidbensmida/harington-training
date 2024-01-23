package org.example.chapitre1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.example.chapitre1.dto.OperationDto;
import org.example.chapitre1.entity.OperationTypeEnum;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OperationControllerTest {

    private static final String API_GET_AND_DELETE_OPERATION_BY_ID = "/api/v1/operations/{id}";
    private static final String API_CREATE_AND_GET_ALL_OPERATION = "/api/v1/operations";

    @Inject
    protected MockMvc mockMvc;


    /*************************************************** Get operation by id *****************************************************************/

    @Test
    @Order(1)
    public void should_return_ok_when_find_operation_by_id_and_operation_exist() throws Exception {
        mockMvc.perform(get(API_GET_AND_DELETE_OPERATION_BY_ID, 102)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_not_found_when_find_operation_by_id_and_operation_does_not_exist() throws Exception {
        mockMvc.perform(get(API_GET_AND_DELETE_OPERATION_BY_ID, 7000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /*************************************************** Create new operation *****************************************************************/
    @Test
    public void should_return_ok_when_create_new_operation() throws Exception {
        OperationDto operationDto = OperationDto.builder().accountId(101L).operationType(OperationTypeEnum.DEPOSIT).amount(500.0F).build();
        mockMvc.perform(post(API_CREATE_AND_GET_ALL_OPERATION)
                        .content(new ObjectMapper().writeValueAsString(operationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    /*************************************************** Get all operations *****************************************************************/
    @Test
    public void should_return_ok_when_find_all_operations() throws Exception {
        mockMvc.perform(get(API_CREATE_AND_GET_ALL_OPERATION)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    /*************************************************** delete operation *****************************************************************/
    @Test
    public void should_return_ok_when_delete_operation_by_id() throws Exception {
        mockMvc.perform(delete(API_GET_AND_DELETE_OPERATION_BY_ID, 101)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_not_found_when_delete_operation_by_id_and_operation_does_not_exist() throws Exception {
        mockMvc.perform(delete(API_GET_AND_DELETE_OPERATION_BY_ID, 7000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}