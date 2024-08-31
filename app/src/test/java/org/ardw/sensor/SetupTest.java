package org.ardw.sensor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javafx.application.Application;
import org.llschall.ardwloop.ArdwloopStarter;

public class SetupTest {

    @Test
    public void checkJavaFx() {
        Assertions.assertFalse(Application.class.getName().isBlank());
    }


    @Test
    public void checkArdwloop() {
        Assertions.assertEquals("0.1.5", ArdwloopStarter.VERSION);
        Assertions.assertEquals(1001, ArdwloopStarter.VERSION_INT);
    }
}
