package org.ardw.sensor;

import org.llschall.ardwloop.IArdwProgram;
import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.structure.data.LoopData;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.data.SerialVector;
import org.llschall.ardwloop.structure.data.SetupData;

class Program implements IArdwProgram {

    private Model model;

    Program(Model model) {
        this.model = model;
    }

    @Override
    public SetupData ardwSetup(SetupData setupData) {
        return new SetupData(new SerialData(0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0));
    }

    @Override
    public LoopData ardwLoop(LoopData in) {
        SerialVector b = in.getData().b;
        float humd = b.v;
        float temp = b.w;

        update(temp, humd);

        int av = 0;
        SerialData data = new SerialData(0,
                av, 0, 0, 0, 0,
                0, 0, 0, 0, 0);

        StructureTimer.get().delayMs(2_000);

        return new LoopData(data);
    }

    private void update(float temp, float humd) {
        if (temp == 0) {
            System.out.println("*");
            return;
        }

        float t = temp / 10;
        float h = humd / 10;

        model.measures.add(new Measure(System.currentTimeMillis(), t, h));
    }

    @Override
    public int getRc() {
        return 2;
    }

    @Override
    public int getSc() {
        return 2;
    }

    @Override
    public int getReadDelayMs() {
        return 0;
    }

    @Override
    public int getPostDelayMs() {
        return 20000;
    }
}