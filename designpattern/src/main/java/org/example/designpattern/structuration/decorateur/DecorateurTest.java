package org.example.designpattern.structuration.decorateur;

public class DecorateurTest {

    public static void main(String[] args) {
        Boisson cafeSimple = new Cafe();
        cafeSimple.preparer();
        System.out.println();

        Boisson cafeAvecLait = new DecorateurLait(cafeSimple);
        cafeAvecLait.preparer();
    }
}
