package org.example.solid.d;

public class Switch {
    private Device device;

    public Switch(Device device) {
        this.device = device;
    }

    public void press() {
        if (device != null) {
            device.turnOn();
        }
    }

    public void release() {
        if (device != null) {
            device.turnOff();
        }
    }
}
