package org.example.designpattern.behavioral.observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Subject {
    String property="initial";

    PropertyChangeSupport pcs = new  PropertyChangeSupport(this);
    public void addObserver(PropertyChangeListener l) {
        pcs.addPropertyChangeListener("property", l);
    }

    public void setProperty(String val) {
        String old = property;
        property = val;
        pcs.firePropertyChange("theProperty", old, val);

    }

    public String toString() { return "The subject object"; };

}
