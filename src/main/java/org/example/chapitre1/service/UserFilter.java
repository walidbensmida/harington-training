package org.example.chapitre1.service;

import org.example.chapitre1.entity.User;

@FunctionalInterface
public interface UserFilter {
    boolean isExist(User user);

}
