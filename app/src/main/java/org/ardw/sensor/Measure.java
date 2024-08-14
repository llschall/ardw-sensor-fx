package org.ardw.sensor;

class Measure {
    
    final long timeMs;

    final float temperature;

    final float humidity;

    Measure(long nanoTime, float temperature, float humidity) {
        this.timeMs = nanoTime;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
