package org.example.designpattern.behavioral.strategy.context;

public class ConcreteStrategyB implements Strategy {
    @Override
    public void executeStrategy() {
        System.out.println("Execute strategy B");
    }
}
