package org.example.designpattern.behavioral.strategy.context;

public class Context {
     private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeOperation() {
        if (strategy != null) {
            strategy.executeStrategy();
        } else {
            System.out.println("Strategy undefined");
        }
    }
}
