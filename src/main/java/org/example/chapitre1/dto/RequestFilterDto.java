package org.example.chapitre1.dto;

import lombok.Data;

@Data
public class RequestFilterDto {
    private FilterType filter;
    private String value;
}
