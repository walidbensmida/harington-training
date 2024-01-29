package org.example.chapitre1.service;

import org.example.chapitre1.dto.UserRequest;
import org.example.chapitre1.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class UserPredicateImpl implements UserPredicate {
    @Override
    public Predicate<User> buildPredicate(UserRequest userRequest) {
        Predicate<User> predicate = user -> true; // initial predicate (true for no filter)

        if (userRequest.getFirstName() != null && !userRequest.getFirstName().isBlank()) {
            predicate = predicate.and(user -> user.getFirstName().contains(userRequest.getFirstName()));
        }

        if (userRequest.getLastName() != null && !userRequest.getLastName().isBlank()) {
            predicate = predicate.and(user -> user.getLastName().contains(userRequest.getLastName()));
        }

        if (userRequest.getEmail() != null && !userRequest.getEmail().isBlank()) {
            predicate = predicate.and(user -> user.getEmail().contains(userRequest.getEmail()));
        }

        if (userRequest.getRole() != null && !userRequest.getRole().isBlank()) {
            predicate = predicate.and(user -> user.getRole().toString().contains(userRequest.getRole()));
        }

        return predicate;
    }
}
