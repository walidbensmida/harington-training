package org.example.chapitre1.dto;

import lombok.Data;

@Data
public class SearchRequestDto {
    private String columnSearch;
    private String valueSearch;
}
