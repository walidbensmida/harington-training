package org.example.chapitre1.controller;

import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.ListRequestFilterDto;
import org.example.chapitre1.dto.RequestSpecificationDto;
import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.service.UserService;
import org.example.chapitre1.service.searchCriteria.FilterSpecificationService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-filters")
@RequiredArgsConstructor
public class FilterController {

    private final UserService userService;
    private final FilterSpecificationService filterSpecificationService;

    @PostMapping("/specification")
    public ResponseEntity<List<UserDto>> getUsers(@RequestBody RequestSpecificationDto requestSpecificationDto) {
        Specification specification = filterSpecificationService.getSearchSpeciation(requestSpecificationDto.getSearchRequestDto(), requestSpecificationDto.getGlobalOperator());
        return ResponseEntity.ok().body(userService.findAll(specification));
    }

    @PostMapping
    public ResponseEntity<List<UserDto>> getUsersByFunctionalInterface(@RequestBody ListRequestFilterDto listRequestFilterDto) {
        return ResponseEntity.ok().body(userService.findAllByFilter(listRequestFilterDto.getRequestFilterDtoList(), listRequestFilterDto.getOperationType()));
    }

}
