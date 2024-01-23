package org.example.designpattern.structuration.adapter;

public class AdaptaterCanard implements Barboter {

    private Canard canard;

    public AdaptaterCanard(Canard canard) {
        this.canard = canard;
    }

    @Override
    public void barboter() {
        System.out.println("Frapper les ailes dans l'eau");// Utilise la méthode existante pour implémenter la nouvelle méthode
    }
}
