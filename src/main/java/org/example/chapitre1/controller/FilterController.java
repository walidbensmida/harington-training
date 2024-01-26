package org.example.chapitre1.controller;

import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.RequestDto;
import org.example.chapitre1.entity.User;
import org.example.chapitre1.repository.UserRepository;
import org.example.chapitre1.service.searchCriteria.FilterSpecificationService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/filter")
@RequiredArgsConstructor
public class FilterController {

    private final UserRepository userRepository;
    private final FilterSpecificationService filterSpecificationService;

        @PostMapping("/specification")
    public List<User> getUsers(@RequestBody RequestDto requestDto) {
        Specification specification = filterSpecificationService.getSearchSpeciation(requestDto.getSearchRequestDto(),requestDto.getGlobOperator());
        return userRepository.findAll(specification);
    }


}
