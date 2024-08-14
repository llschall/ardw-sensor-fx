package org.ardw.sensor;

import java.util.concurrent.ConcurrentLinkedQueue;

class Model {

    final ConcurrentLinkedQueue<Measure> measures = new ConcurrentLinkedQueue<>();


}
