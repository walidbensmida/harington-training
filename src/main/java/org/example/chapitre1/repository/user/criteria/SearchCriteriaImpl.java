package org.example.chapitre1.repository.user.criteria;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
class FirstNameCriteriaImpl implements IFirstNameCriteria {
    @Override
    public Consumer<StringBuilder> build(String firstName) {
        return builder -> {
            if (firstName != null && !firstName.isBlank()) {
                builder.append(" AND first_name LIKE '%").append(firstName).append("%'");
            }
        };
    }
}

@Component
class LastNameCriteriaImpl implements ILastNameCriteria {
    @Override
    public Consumer<StringBuilder> build(String lastName) {
        return builder -> {
            if (lastName != null && !lastName.isBlank()) {
                builder.append(" AND last_name LIKE '%").append(lastName).append("%'");
            }
        };
    }
}

@Component
class EmailCriteriaImpl implements IEmailCriteria {
    @Override
    public Consumer<StringBuilder> build(String email) {
        return builder -> {
            if (email != null && !email.isBlank()) {
                builder.append(" AND email LIKE '%").append(email).append("%'");
            }
        };
    }
}

@Component
class RoleCriteriaImpl implements IRoleCriteria {
    @Override
    public Consumer<StringBuilder> build(String role) {
        return builder -> {
            if (role != null && !role.isBlank()) {
                builder.append(" AND role LIKE '%").append(role).append("%'");
            }
        };
    }
}


