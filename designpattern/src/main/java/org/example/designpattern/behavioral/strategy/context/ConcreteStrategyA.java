package org.example.designpattern.behavioral.strategy.context;

public class ConcreteStrategyA implements Strategy {
    @Override
    public void executeStrategy() {
        System.out.println("Execute strategy A");
    }
}
