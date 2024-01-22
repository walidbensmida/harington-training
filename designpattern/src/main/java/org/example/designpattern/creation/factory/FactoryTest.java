package org.example.designpattern.creation.factory;

/**
 * The main method that serves as an entry point for the factory application.
 *
 * @throws Exception If an exception occurs during the execution.
 */
public class FactoryTest {
    public static void main(String[] args) throws Exception {

        IProductFactory productFactory = new ProductFactory();
        Product car = productFactory.createProduct(ProductTypeEnum.CAR);
        Product bicycle = productFactory.createProduct(ProductTypeEnum.BICYCLE);
        // Appel de la méthode fabriquer sur la voiture
        car.produce();
        // Appel de la méthode fabriquer sur le vélo
        bicycle.produce();
    }
}
