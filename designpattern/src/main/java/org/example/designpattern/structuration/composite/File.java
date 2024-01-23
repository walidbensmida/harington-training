package org.example.designpattern.structuration.composite;

public class File implements FileComponent {
    public File(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public void display() {
        System.out.println("Fichier : " + name);
    }
}
