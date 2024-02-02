package org.example.chapitre1.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListRequestFilterDto {
    private List<RequestFilterDto> requestFilterDtoList;
    private RequestSpecificationDto.GlobalOperator operationType;
}
