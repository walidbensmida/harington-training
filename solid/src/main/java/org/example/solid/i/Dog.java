package org.example.solid.i;

public class Dog implements LandAnimal{
    @Override
    public void run() {
        System.out.println("Dog is running");
    }

    @Override
    public void makeSound() {
        System.out.println("Le chien aboie.");
    }
}
