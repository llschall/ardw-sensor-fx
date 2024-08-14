package org.ardw.sensor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelTest {

    @Test
    public void testModel() {

        Model model = new Model(10);
        Assertions.assertTrue(model.isEmpty());

        model.add(new Measure(10, 10, 10));

        for (Measure measure : model) {
            Assertions.assertEquals(10, measure.timeMs);
        }

        Assertions.assertTrue(model.isEmpty());
    }

}
