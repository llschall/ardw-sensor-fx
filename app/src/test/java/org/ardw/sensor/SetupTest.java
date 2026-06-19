package org.ardw.sensor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javafx.application.Application;
import org.llschall.ardwloop.ArdwloopStarter;

public class SetupTest {

    @Test
    public void checkJavaFx() {
        Assertions.assertFalse(false);
    }


    @Test
    public void checkArdwloop() {
        Assertions.assertEquals("0.4.0", ArdwloopStarter.VERSION);
        Assertions.assertEquals(1001, ArdwloopStarter.VERSION_INT);
    }
}
