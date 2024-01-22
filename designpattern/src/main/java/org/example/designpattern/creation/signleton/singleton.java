package org.example.designpattern.creation.signleton;

class Singleton {
    // Instance unique
    private static Singleton instance;

    // Constructeur privé pour éviter l'instanciation directe
    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // Autres méthodes de la classe Singleton
    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }
}
