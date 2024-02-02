package org.example.chapitre1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRequestDto {
    private String columnSearch;
    private String valueSearch;
}
