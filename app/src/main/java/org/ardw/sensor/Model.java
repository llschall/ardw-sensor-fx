package org.ardw.sensor;

import java.util.concurrent.ConcurrentLinkedQueue;

class Model {

    private final ConcurrentLinkedQueue<Measure> measures = new ConcurrentLinkedQueue<>();


    boolean isEmpty() {
        return measures.isEmpty();
    }

    Measure peek() {
        return measures.peek();
    }

    Measure remove() {
        return measures.remove();
    }

    void add(Measure measure) {
        measures.add(measure);
    }
}
