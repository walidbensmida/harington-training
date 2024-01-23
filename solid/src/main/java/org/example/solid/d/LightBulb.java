package org.example.solid.d;

public class LightBulb implements Device{

    @Override
    public void turnOn() {
        System.out.println("Ampoule allumée.");
    }

    @Override
    public void turnOff() {
        System.out.println("Ampoule éteinte.");
    }
}
