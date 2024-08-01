package org.ardw.sensor;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.llschall.ardwloop.ArdwloopStarter;
import org.llschall.ardwloop.IArdwProgram;
import org.llschall.ardwloop.structure.model.ArdwloopModel;

public class Controller {

    @FXML
    AreaChart<Number, Number> chartT, chartH;

    @FXML
    Label labelT, labelH;

    @FXML
    TextArea console;

    Measure zero = null;

    ObservableList<AreaChart.Data<Number, Number>> dataT, dataH, zeroT, zeroH;

    public void start() {
        log("Java version is " + System.getProperty("java.version"));

        AreaChart.Series<Number, Number> serieT = new AreaChart.Series<>();
        AreaChart.Series<Number, Number> serieH = new AreaChart.Series<>();

        AreaChart.Series<Number, Number> serieTZ = new AreaChart.Series<>();
        AreaChart.Series<Number, Number> serieHZ = new AreaChart.Series<>();

        dataT = serieT.getData();
        dataH = serieH.getData();

        zeroT = serieTZ.getData();
        zeroH = serieHZ.getData();

        chartT.getData().add(serieT);
        chartT.getData().add(serieTZ);

        chartH.getData().add(serieH);
        chartH.getData().add(serieHZ);

        NumberAxis yAxis = (NumberAxis) chartT.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(20);
        yAxis.setUpperBound(32);

        yAxis.setTickMarkVisible(true);
        yAxis.setTickUnit(1);

        IArdwProgram program = new Program();
        ArdwloopModel model = ArdwloopStarter.get().start(program);

        new AppThread(this).start();
    }

    void process() {

        if (Measure.measures.isEmpty()) {
            return;
        }

        if (zero == null) {
            log("Zero is now.");
            zero = Measure.measures.peek();
        }

        while (!Measure.measures.isEmpty()) {

            Measure measure = Measure.measures.remove();
            log("" + measure.temperature);

            long time = (measure.timeMs - zero.timeMs) / 1000;

            Platform.runLater(() -> {

                labelT.setText("" + measure.temperature);
                labelH.setText("" + measure.humidity);

                dataT.add(new AreaChart.Data<>(time, measure.temperature));
                zeroT.add(new AreaChart.Data<>(time, zero.temperature));

                dataH.add(new AreaChart.Data<>(time, measure.humidity));
                zeroH.add(new AreaChart.Data<>(time, zero.humidity));

            });

        }
    }

    void log(String msg) {
        console.appendText(msg + "\n");
    }

}