package org.example.designpattern.creation.factory;


/**
 * A concrete implementation of the IProductFactory interface for creating products.
 * This factory class defines how to create specific types of products.
 */
public class ProductFactory implements IProductFactory {
    /**
     * Creates a product based on the provided product type.
     *
     * @param productTypeEnum The type of product to be created.
     * @return A product instance corresponding to the specified type.
     * @throws IllegalStateException If an unexpected product type is encountered.
     */
    @Override
    public Product createProduct(ProductTypeEnum productTypeEnum) {
        switch (productTypeEnum){
            case CAR -> {
                return new Car();
            }
            case BICYCLE -> {
                return new Bicycle();
            }
            default -> throw new IllegalStateException("Unexpected value: " + productTypeEnum);
        }
    }
}
