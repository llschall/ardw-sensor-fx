package org.ardw.sensor;

public class AppThread extends Thread {

    final Controller controller;

    public AppThread(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {

        while (!isInterrupted()) {
            controller.process();

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
