package org.ardw.sensor;

import org.llschall.ardwloop.IArdwProgram;
import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.value.SerialData;
import org.llschall.ardwloop.value.V;


class Program implements IArdwProgram {

    private final Model model;

    Program(Model model) {
        this.model = model;
    }

    // See https://github.com/llschall/ardwloop/wiki#ardwsetup
    @Override
    public SerialData ardwSetup(SerialData setupData) {
        return new SerialData(0, 0, 0, 0, 0);
    }

    // See https://github.com/llschall/ardwloop/wiki#ardwloop
    @Override
    public SerialData ardwLoop(SerialData in) {
        V b = in.b;
        float humd = b.v;
        float temp = b.w;

        update(temp, humd);

        int av = 0;
        SerialData data = new SerialData(av, 0, 0, 0, 0);
        StructureTimer.get().delayMs(3_000);
        return data;
    }

    private void update(float temp, float humd) {
        if (temp == 0) {
            return;
        }

        float t = temp / 10;
        float h = humd / 10;

        model.add(new Measure(System.currentTimeMillis(), t, h));
    }
}