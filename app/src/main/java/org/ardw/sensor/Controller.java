package org.ardw.sensor;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextArea;
import org.llschall.ardwloop.ArdwloopStarter;
import org.llschall.ardwloop.IArdwProgram;
import org.llschall.ardwloop.structure.model.ArdwloopModel;

public class Controller {

    @FXML
    AreaChart<Number, Number> chartT, chartH;

    @FXML
    TextArea console;

    ObservableList<AreaChart.Data<Number, Number>> dataT, dataH;

    public void start() {
        log("Java version is " + System.getProperty("java.version"));

        AreaChart.Series<Number, Number> serieT = new AreaChart.Series<>();
        AreaChart.Series<Number, Number> serieH = new AreaChart.Series<>();

        dataT = serieT.getData();
        dataH = serieH.getData();

        chartT.getData().add(serieT);
        chartH.getData().add(serieH);

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
        while (!Measure.measures.isEmpty()) {

            Measure measure = Measure.measures.remove();
            log("" + measure.temperature);

            long time = (measure.timeMs - Measure.ZERO_MS) / 1000;

            Platform.runLater(() -> {
                dataT.add(new AreaChart.Data<>(time, measure.temperature));
                dataH.add(new AreaChart.Data<>(time, measure.humidity));
            });

        }
    }

    void log(String msg) {
        console.appendText(msg + "\n");
    }

}