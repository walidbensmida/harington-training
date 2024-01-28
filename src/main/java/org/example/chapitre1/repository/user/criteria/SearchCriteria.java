package org.example.chapitre1.repository.user.criteria;

import java.util.function.Consumer;


@FunctionalInterface
interface IFirstNameCriteria {
    Consumer<StringBuilder> build(String nom);
}

@FunctionalInterface
interface ILastNameCriteria {
    Consumer<StringBuilder> build(String email);
}

@FunctionalInterface
interface IEmailCriteria {
    Consumer<StringBuilder> build(String ville);
}

@FunctionalInterface
interface IRoleCriteria {
    Consumer<StringBuilder> build(String ville);
}