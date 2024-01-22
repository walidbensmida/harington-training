package org.example.designpattern.structuration.decorateur;

public class DecorateurLait extends Cafe{

    private Boisson boisson;

    public DecorateurLait(Boisson boisson) {
        this.boisson = boisson;
    }

    @Override
    public void preparer(){
        boisson.preparer();
        System.out.print(" + Lait");
    }
}
