package org.ardw.sensor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelTest {

    @Test
    public void testModel() {

        Model model = new Model(10);

        Assertions.assertTrue(model.isEmpty());


    }

}
