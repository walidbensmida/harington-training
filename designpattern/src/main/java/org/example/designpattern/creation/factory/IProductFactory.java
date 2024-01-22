package org.example.designpattern.creation.factory;

/**
 * An interface representing a factory for creating products.
 * Implementations of this interface define how to create specific types of products.
 */
public interface IProductFactory {
    /**
     * Creates a product based on the provided product type.
     *
     * @param productTypeEnum The type of product to be created.
     * @return A product instance corresponding to the specified type.
     * @throws Exception If an error occurs during the product creation process.
     */
    Product createProduct(ProductTypeEnum productTypeEnum) throws Exception;
}
