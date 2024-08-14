package org.ardw.sensor;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

class Model implements Iterable<Measure> {

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

    @NotNull
    @Override
    public Iterator<Measure> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return !measures.isEmpty();
            }

            @Override
            public Measure next() {
                return measures.remove();
            }
        };
    }
}
