package org.example.chapitre1.repository.user.criteria;

import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.UserRequest;
import org.example.chapitre1.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserJdbcRepositoryImpl implements IUserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private final FirstNameCriteriaImpl firstNameCriteria;
    private final LastNameCriteriaImpl lastNameCriteria;
    private final EmailCriteriaImpl emailCriteria;
    private final RoleCriteriaImpl roleCriteria;


    @Override
    public List<User> dynamicSearch(UserRequest userRequest) {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_user WHERE 1=1");

        // Functional Interface to build our query
        applyCriteria(sql, firstNameCriteria.build(userRequest.getFirstName()));
        applyCriteria(sql, lastNameCriteria.build(userRequest.getLastName()));
        applyCriteria(sql, emailCriteria.build(userRequest.getEmail()));
        applyCriteria(sql, roleCriteria.build(userRequest.getRole()));

        return jdbcTemplate.query(sql.toString(), new UserRowMapper());
    }
}
