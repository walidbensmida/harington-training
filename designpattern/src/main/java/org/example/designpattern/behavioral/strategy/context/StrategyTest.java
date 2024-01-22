package org.example.designpattern.behavioral.strategy.context;

public class StrategyTest {
    public static void main(String[] args) {
        Context context = new Context();
        context.executeOperation();

        context.setStrategy(new ConcreteStrategyA());
        context.executeOperation();

        // Utilisation de la stratégie B
        context.setStrategy(new ConcreteStrategyB());
        context.executeOperation();
    }
}
