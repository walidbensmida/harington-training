package org.example.designpattern.structuration.composite;

public class CompositeTest {
    public static void main(String[] args) {
        FileComponent file1 = new File("Document.txt");
        FileComponent file2 = new File("Image.jpg");

        Folder folder = new Folder("Mes Documents");
        folder.add(file1);
        folder.add(file2);

        Folder subFolder = new Folder("Images");
        subFolder.add(new File("Vacances.jpg"));
        subFolder.add(new File("Famille.jpg"));

        folder.add(subFolder);

        folder.display();
    }
}
