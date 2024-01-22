package org.example.designpattern.creation.signleton;

public class SingletonTest {
    public static void main(String[] args) {
        // Obtention de l'instance unique
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();

        // Les deux instances pointent vers le même objet
        System.out.println("Are both instances the same? " + (singleton1 == singleton2));

        // Utilisation de l'instance unique
        singleton1.showMessage();
        singleton2.showMessage();

    }
}