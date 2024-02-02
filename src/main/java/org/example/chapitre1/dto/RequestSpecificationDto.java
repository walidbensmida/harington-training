package org.example.chapitre1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestSpecificationDto {
    private List<SearchRequestDto> searchRequestDto;
    private GlobalOperator globalOperator;


    public enum GlobalOperator {
        AND, OR
    }
}
