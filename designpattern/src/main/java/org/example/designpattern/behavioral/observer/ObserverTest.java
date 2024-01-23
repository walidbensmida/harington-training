package org.example.designpattern.behavioral.observer;

public class ObserverTest {
    public static void main(String[] args) {

        Subject s = new Subject();

        Concerned o = new Concerned();

        s.addObserver(o);

        s.setProperty("new");

    }
}
