package org.ardw.sensor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javafx.application.Application;

public class SetupTest {

    @Test
    public void checkJavaFx() {
        Assertions.assertFalse(Application.class.getName().isBlank());
    }
}
