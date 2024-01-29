package org.example.chapitre1.repository.user.criteria;

import org.example.chapitre1.dto.UserRequest;
import org.example.chapitre1.entity.User;

import java.util.List;
import java.util.function.Consumer;

@FunctionalInterface
public interface IUserJdbcRepository {
    List<User> dynamicSearch(UserRequest userRequest);

    default void applyCriteria(StringBuilder sql, Consumer<StringBuilder> criteria) {
        if (criteria != null) {
            criteria.accept(sql);
        }
    }
}
