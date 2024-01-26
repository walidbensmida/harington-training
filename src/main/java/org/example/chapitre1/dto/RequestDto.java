package org.example.chapitre1.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestDto {
    private List<SearchRequestDto> searchRequestDto;
    private GlobOperator globOperator;


    public enum GlobOperator {
        AND, OR
    }
}
