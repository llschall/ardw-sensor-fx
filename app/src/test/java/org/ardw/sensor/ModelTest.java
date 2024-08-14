package org.ardw.sensor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    public void testModel() {

        Model model = new Model(10);
        assertTrue(model.isEmpty());

        model.add(new Measure(10, 10, 10));

        for (Measure measure : model) {
            Assertions.assertEquals(10, measure.timeMs);
        }

        assertTrue(model.isEmpty());
    }

    @Test
    public void testDelta() {

        Model model = new Model(3);
        assertTrue(model.isEmpty());

        model.add(new Measure(100, 20, 60));
        model.add(new Measure(101, 20, 60));
        model.add(new Measure(102, 20, 60));
        model.add(new Measure(103, 20, 60));
        model.add(new Measure(104, 20, 60));

        Iterator<Measure> iterator = model.iterator();

        assertEquals(100, iterator.next().timeMs);
        assertEquals(100, model.delta().timeMs);

        assertEquals(101, iterator.next().timeMs);
        assertEquals(100, model.delta().timeMs);

        assertEquals(102, iterator.next().timeMs);
        assertEquals(100, model.delta().timeMs);

        assertEquals(103, iterator.next().timeMs);
        assertEquals(101, model.delta().timeMs);

        model.add(new Measure(105, 20, 60));
        model.add(new Measure(106, 20, 60));

        assertEquals(104, iterator.next().timeMs);
        assertEquals(102, model.delta().timeMs);

        assertEquals(105, iterator.next().timeMs);
        assertEquals(103, model.delta().timeMs);

        assertEquals(106, iterator.next().timeMs);
        assertEquals(104, model.delta().timeMs);
        
        assertFalse(iterator.hasNext());

        model.add(new Measure(107, 20, 60));

        assertEquals(107, iterator.next().timeMs);
        assertEquals(105, model.delta().timeMs);
        assertFalse(iterator.hasNext());
    }

}
