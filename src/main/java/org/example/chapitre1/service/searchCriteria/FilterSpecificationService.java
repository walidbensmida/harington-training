package org.example.chapitre1.service.searchCriteria;

import jakarta.persistence.criteria.Predicate;
import org.example.chapitre1.dto.RequestDto;
import org.example.chapitre1.dto.SearchRequestDto;
import org.example.chapitre1.exception.UnsupportedOperationTypeException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilterSpecificationService<T> {


    public Specification<T> getSearchSpeciation(List<SearchRequestDto> searchRequestDtos, RequestDto.GlobOperator globOperator) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (SearchRequestDto searchRequestDto : searchRequestDtos) {
                Predicate predicate = criteriaBuilder.equal(root.get(searchRequestDto.getColumnSearch()), searchRequestDto.getValueSearch());
                predicates.add(predicate);
            }
            switch (globOperator) {
                case AND:
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                case OR:
                    return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
                default:
                    throw new RuntimeException(new UnsupportedOperationTypeException());
            }

        };
    }
}
