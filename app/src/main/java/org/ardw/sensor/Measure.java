package org.ardw.sensor;

import java.util.concurrent.ConcurrentLinkedQueue;

class Measure {

    final static long ZERO_MS = System.currentTimeMillis();

    final static ConcurrentLinkedQueue<Measure> measures = new ConcurrentLinkedQueue<>();

    final long timeMs;

    final float temperature;

    final float humidity;

    Measure(long nanoTime, float temperature, float humidity) {
        this.timeMs = nanoTime;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
