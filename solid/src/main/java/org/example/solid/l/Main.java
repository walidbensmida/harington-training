package org.example.solid.l;

public class Main {
    public static void main(String[] args) {
        Shape rectangle = new Rectangle(5, 10);
        Shape square = new Square(4);

        printArea(rectangle);
        printArea(square);
    }

    static void printArea(Shape shape) {
        System.out.println("Area: " + shape.calculateArea());
    }

}
