package org.ardw.sensor;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

class Model {

    private final int lastCnt;

    private final ConcurrentLinkedQueue<Measure> measures = new ConcurrentLinkedQueue<>();

    private final LinkedList<Measure> list = new LinkedList<>();

    public Model(int lastCnt) {
        this.lastCnt = lastCnt;
    }

    boolean isEmpty() {
        return measures.isEmpty();
    }

    Measure peek() {
        return measures.peek();
    }

    Measure remove() {
        Measure measure = measures.remove();
        addLast(measure);
        return measure;
    }

    void add(Measure measure) {
        measures.add(measure);
    }

    void addLast(Measure measure) {
        list.addLast(measure);
        while (list.size() > lastCnt) {
            list.removeFirst();
        }
    }

    Measure listPeek() {
        return list.peek();
    }

}
