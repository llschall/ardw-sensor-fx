package org.ardw.sensor;

import org.llschall.ardwloop.motor.AbstractLoop;

public class AppLoop extends AbstractLoop {

    final Controller controller;

    public AppLoop(Controller controller) {
        super(AppLoop.class.getSimpleName());
        this.controller = controller;
    }

    @Override
    public void loop() {
        controller.process();
    }

    @Override
    public void close() { // do nothing
    }
}
