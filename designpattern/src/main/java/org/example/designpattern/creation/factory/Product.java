package org.example.designpattern.creation.factory;


/**
 * An interface representing a generic product.
 * Implementations of this interface define the specific behavior of a product.
 */
public interface Product {
    /**
     * Performs the production process for the product.
     * The exact behavior is defined by the implementing class.
     */
    void produce();
}
