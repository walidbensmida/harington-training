package org.example.chapitre1.service;

import org.example.chapitre1.entity.User;

@FunctionalInterface
public interface UserFilter {
    boolean isExist(User user);

    default UserFilter and(UserFilter other) {
        return user -> this.isExist(user) && other.isExist(user);
    }

    default UserFilter or(UserFilter other) {
        return user -> this.isExist(user) || other.isExist(user);
    }

}
