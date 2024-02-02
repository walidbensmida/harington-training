package org.example.chapitre1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.example.chapitre1.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "user", authorities = {"USER"})
class FilterControllerTest {

    private static final String API_FILTER_AND_GET_USERS = "/api/v1/user-filters";
    private static final String API_FILTER_USING_SPECIFICATION_AND_GET_USERS = "/api/v1/user-filters/specification";

    @Inject
    protected MockMvc mockMvc;


    @Test
    void should_filter_users_when_filter_user_by_filter_given_filter_type_firstname_lastname_operator_or_given_filtered_users() throws Exception {

        FilterType filterFirstname = FilterType.FIRSTNAME;
        FilterType filterLastname = FilterType.LASTNAME;

        RequestFilterDto filterDtoFirstname = RequestFilterDto.builder().filter(filterFirstname).value("firstnametest").build();
        RequestFilterDto filterDtoLastname = RequestFilterDto.builder().filter(filterLastname).value("lastnametest").build();
        RequestSpecificationDto.GlobalOperator or = RequestSpecificationDto.GlobalOperator.OR;

        List<RequestFilterDto> requestFilterDtoList = Arrays.asList(filterDtoFirstname, filterDtoLastname);
        ListRequestFilterDto listRequestFilterDto = ListRequestFilterDto.builder().
                requestFilterDtoList(requestFilterDtoList).
                operationType(or)
                .build();

        mockMvc.perform(post(API_FILTER_AND_GET_USERS)
                        .content(new ObjectMapper().writeValueAsString(listRequestFilterDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].firstName").value("firstnametest"))
                .andExpect(jsonPath("$[0].lastName").value("lastnametest"))
                .andExpect(jsonPath("$[1].firstName").value("anotherExpectedFirstName"))
                .andExpect(jsonPath("$[1].lastName").value("lastnametest"))
                .andExpect(jsonPath("$.size()").value(2));

    }

    @Test
    void should_filter_users_when_search_user_by_filter_when_filter_type_firstname_lastname_operator_and_given_filtered_users() throws Exception {

        FilterType filterFirstname = FilterType.FIRSTNAME;
        FilterType filterLastname = FilterType.LASTNAME;

        RequestFilterDto filterDtoFirstname = RequestFilterDto.builder().filter(filterFirstname).value("firstnametest").build();
        RequestFilterDto filterDtoLastname = RequestFilterDto.builder().filter(filterLastname).value("lastnametest").build();
        RequestSpecificationDto.GlobalOperator or = RequestSpecificationDto.GlobalOperator.AND;

        List<RequestFilterDto> requestFilterDtoList = Arrays.asList(filterDtoFirstname, filterDtoLastname);
        ListRequestFilterDto listRequestFilterDto = ListRequestFilterDto.builder().
                requestFilterDtoList(requestFilterDtoList).
                operationType(or)
                .build();

        mockMvc.perform(post(API_FILTER_AND_GET_USERS)
                        .content(new ObjectMapper().writeValueAsString(listRequestFilterDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].firstName").value("firstnametest"))
                .andExpect(jsonPath("$[0].lastName").value("lastnametest"))
                .andExpect(jsonPath("$.size()").value(1));

    }

    @Test
    void should_filter_users_when_search_user_by_filter_when_using_specification_given_filtered_users() throws Exception {

        SearchRequestDto searchRequestDto1 = new SearchRequestDto("firstName","firstnametest");
        SearchRequestDto searchRequestDto2 = new SearchRequestDto("lastName","lastnametest");
        List<SearchRequestDto> searchRequestDtos = Arrays.asList(searchRequestDto1,searchRequestDto2);
        RequestSpecificationDto.GlobalOperator and = RequestSpecificationDto.GlobalOperator.AND;
        RequestSpecificationDto.GlobalOperator or = RequestSpecificationDto.GlobalOperator.OR;
        RequestSpecificationDto requestSpecificationDtoAnd = new RequestSpecificationDto(searchRequestDtos,and);
        RequestSpecificationDto requestSpecificationDtoOr= new RequestSpecificationDto(searchRequestDtos,or);


        mockMvc.perform(post(API_FILTER_USING_SPECIFICATION_AND_GET_USERS)
                        .content(new ObjectMapper().writeValueAsString(requestSpecificationDtoAnd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].firstName").value("firstnametest"))
                .andExpect(jsonPath("$[0].lastName").value("lastnametest"))
                .andExpect(jsonPath("$.size()").value(1));

        mockMvc.perform(post(API_FILTER_USING_SPECIFICATION_AND_GET_USERS)
                        .content(new ObjectMapper().writeValueAsString(requestSpecificationDtoOr))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].firstName").value("firstnametest"))
                .andExpect(jsonPath("$[0].lastName").value("lastnametest"))
                .andExpect(jsonPath("$[1].firstName").value("anotherExpectedFirstName"))
                .andExpect(jsonPath("$[1].lastName").value("lastnametest"))
                .andExpect(jsonPath("$.size()").value(2));

    }
}