package org.example.designpattern.creation.factory;

/**
 * Represents a concrete implementation of the Product interface for a Car.
 * This class defines the specific behavior of producing a car.
 */
public class Car implements Product {
    @Override
    public void produce() {
        System.out.println("Car produced");
    }
}
