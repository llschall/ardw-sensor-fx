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

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controller {

    @FXML
    AreaChart<Number, Number> chartT, chartH;

    @FXML
    Label labelT, labelH, loopLbl;

    @FXML
    TextArea console;

    Measure zero = null;

    ObservableList<AreaChart.Data<Number, Number>> dataT, dataH, zeroT, zeroH;

    int count;

    public void start() {
        log("Java version is " + System.getProperty("java.version"));
        log(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        log(ManagementFactory.getRuntimeMXBean().getName());

        AreaChart.Series<Number, Number> serieT = new AreaChart.Series<>();
        serieT.setName("Temperature");
        AreaChart.Series<Number, Number> serieH = new AreaChart.Series<>();
        serieH.setName("Humidity");

        AreaChart.Series<Number, Number> serieTZ = new AreaChart.Series<>();
        serieTZ.setName("Zero");
        AreaChart.Series<Number, Number> serieHZ = new AreaChart.Series<>();
        serieHZ.setName("Zero");

        dataT = serieT.getData();
        dataH = serieH.getData();

        zeroT = serieTZ.getData();
        zeroH = serieHZ.getData();

        chartT.getData().add(serieT);
        chartT.getData().add(serieTZ);

        chartH.getData().add(serieH);
        chartH.getData().add(serieHZ);

        setupAxis(chartT, 20, 32, 1);
        setupAxis(chartH, 30, 90, 2);

        IArdwProgram program = new Program();
        ArdwloopModel model = ArdwloopStarter.get().start(program);

        new AppThread(this).start();
    }

    void setupAxis(AreaChart<Number, Number> chart, int min, int max, int tick) {
        NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(min);
        yAxis.setUpperBound(max);

        yAxis.setTickMarkVisible(true);
        yAxis.setTickUnit(tick);
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
            long time = (measure.timeMs - zero.timeMs) / 1000;
            Platform.runLater(() -> {
                loopLbl.setText("" + count++);

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