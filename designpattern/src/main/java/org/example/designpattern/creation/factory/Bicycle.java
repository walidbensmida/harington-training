package org.example.designpattern.creation.factory;

/**
 * Represents a concrete implementation of the Product interface for a Bicycle.
 * This class defines the specific behavior of producing a Bicycle.
 */
public class Bicycle implements Product {
    @Override
    public void produce() {
        System.out.println("Bicycle produced");
    }

}
