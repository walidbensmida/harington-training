package org.example.designpattern.structuration.composite;


import java.util.ArrayList;
import java.util.List;

public class Folder implements FileComponent {
    private String name;
    private List<FileComponent> files = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }


    public void add(FileComponent file) {
        files.add(file);
    }

    @Override
    public void display() {
        System.out.println("Dossier : " + name);
        for (FileComponent file : files) {
            file.display();
        }
    }
}
