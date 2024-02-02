package org.example.chapitre1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestFilterDto {
    private FilterType filter;
    private String value;
}
