package org.example.designpattern.structuration.decorateur;

public class Cafe implements Boisson{
    @Override
    public void preparer() {
        System.out.print("Café");
    }
}
