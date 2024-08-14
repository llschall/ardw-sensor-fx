package org.ardw.sensor;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

class Model {

    private final ConcurrentLinkedQueue<Measure> measures = new ConcurrentLinkedQueue<>();

    private final LinkedList<Measure> list = new LinkedList<>();

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

    void addLast(Measure measure) {
        list.addLast(measure);
    }

    int size() {
        return list.size();
    }

    void removeFirst() {
        list.removeFirst();
    }

    Measure listPeek() {
        return list.peek();
    }
}
