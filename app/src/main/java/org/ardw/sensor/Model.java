package org.ardw.sensor;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

class Model implements Iterable<Measure> {

    final static int TEMP_MIN = 20;
    final static int TEMP_MAX = 32;
    final static int HUMD_MIN = 20;
    final static int HUMD_MAX = 80;

    private final int lastCnt;

    private final ConcurrentLinkedQueue<Measure> measures = new ConcurrentLinkedQueue<>();

    private final LinkedList<Measure> list = new LinkedList<>();

    public Model(int deltaCnt) {
        this.lastCnt = deltaCnt;
    }

    boolean isFakeDelta = true;

    boolean isEmpty() {
        return measures.isEmpty();
    }

    Measure peek() {
        return measures.peek();
    }

    private Measure remove() {
        Measure measure = measures.remove();
        addLast(measure);
        return measure;
    }

    void add(Measure measure) {
        measures.add(measure);
    }

    private void addLast(Measure measure) {
        list.addLast(measure);
        while (list.size() > lastCnt) {
            isFakeDelta = false;
            list.removeFirst();
        }
    }

    boolean isFakeDelta() {
        return isFakeDelta;
    }

    Measure delta() {
        return list.peek();
    }

    @NotNull
    @Override
    public Iterator<Measure> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return !Model.this.isEmpty();
            }

            @Override
            public Measure next() {
                return Model.this.remove();
            }
        };
    }
}
