package org.example.chapitre1.service;

import org.example.chapitre1.dto.UserRequest;
import org.example.chapitre1.entity.User;

import java.util.function.Predicate;

@FunctionalInterface
public interface UserPredicate {
    Predicate<User> buildPredicate(UserRequest userRequest);
}
