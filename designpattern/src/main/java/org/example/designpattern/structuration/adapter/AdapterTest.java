package org.example.designpattern.structuration.adapter;

public class AdapterTest {
    public static void main(String[] args) {
        Canard canard = new Canard();
        canard.cancaner();

        // Utilisation de l'adaptateur pour permettre au canard de barboter
        Barboter canardAdapte = new AdaptaterCanard(canard);
        canardAdapte.barboter();
    }
}
