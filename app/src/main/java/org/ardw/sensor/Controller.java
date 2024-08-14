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
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Controller {

    final Model model = new Model();

    @FXML
    AreaChart<Number, Number> chartT, chartH;

    @FXML
    Label serialLbl, labelT, labelH, labelTZ, labelHZ, labelTD, labelHD, loopLbl;

    @FXML
    TextArea console;

    Measure zero = null;

    boolean xAutoRanging = false;

    ObservableList<AreaChart.Data<Number, Number>> dataT, dataH, zeroT, zeroH, deltaT, deltaH;

    int count;
    private ArdwloopModel ardwMdl;

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

        AreaChart.Series<Number, Number> serieTD = new AreaChart.Series<>();
        serieTD.setName("Delta");
        AreaChart.Series<Number, Number> serieHD = new AreaChart.Series<>();
        serieHD.setName("Delta");


        dataT = serieT.getData();
        dataH = serieH.getData();

        zeroT = serieTZ.getData();
        zeroH = serieHZ.getData();

        deltaT = serieTD.getData();
        deltaH = serieHD.getData();

        chartT.getData().add(serieTZ);
        chartT.getData().add(serieT);
        chartT.getData().add(serieTD);

        chartH.getData().add(serieHZ);
        chartH.getData().add(serieH);
        chartH.getData().add(serieHD);

        setupAxis(chartT, 20, 32, 1);
        setupAxis(chartH, 30, 70, 2);

        IArdwProgram program = new Program(model);
        ardwMdl = ArdwloopStarter.get().start(program);

        new AppThread(this).start();
    }

    void setupAxis(AreaChart<Number, Number> chart, int min, int max, int tick) {
        NumberAxis xAxis = (NumberAxis) chart.getXAxis();
        xAxis.setAutoRanging(false);
        xAxis.setUpperBound(99);

        NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(min);
        yAxis.setUpperBound(max);

        yAxis.setTickMarkVisible(true);
        yAxis.setTickUnit(tick);
    }

    void process() {

        Platform.runLater(() -> {
            if (zero == null) {
                serialLbl.setText(ardwMdl.serialMdl.status.get());
            }
        });

        if (model.isEmpty()) {
            return;
        }

        if (zero == null) {
            log("Zero is now.");
            zero = model.peek();
            Platform.runLater(() -> {
                labelTZ.setText(format(zero.temperature));
                labelHZ.setText(format(zero.humidity));
            });
        }
        while (!model.isEmpty()) {

            Measure measure = model.remove();
            model.addLast(measure);
            while (model.size() > 1000) {
                model.removeFirst();
            }

            Measure delta = model.listPeek();

            long time = (measure.timeMs - zero.timeMs) / 1000;

            if (!xAutoRanging && time > 80) {
                Platform.runLater(() -> {
                    xAutoRanging = true;
                    chartT.getXAxis().setAutoRanging(true);
                    chartH.getXAxis().setAutoRanging(true);
                });
            }

            Platform.runLater(() -> {

                int m = (int) time / 60;
                int s = (int) time - m * 60;
                serialLbl.setText(m + "m " + s + "s");

                loopLbl.setText("#" + count++);

                labelT.setText(format(measure.temperature));
                labelH.setText(format(measure.humidity));

                dataT.add(new AreaChart.Data<>(time, measure.temperature));
                zeroT.add(new AreaChart.Data<>(time, zero.temperature));
                deltaT.add(new AreaChart.Data<>(time, delta.temperature));
                float deltaT = delta.temperature - measure.temperature;
                labelTD.setText(format(deltaT));

                dataH.add(new AreaChart.Data<>(time, measure.humidity));
                zeroH.add(new AreaChart.Data<>(time, zero.humidity));
                deltaH.add(new AreaChart.Data<>(time, delta.humidity));
                float deltaH = delta.humidity - measure.humidity;
                labelHD.setText(format(deltaH));

            });
        }
    }

    String format(float f) {
        DecimalFormat format = new DecimalFormat("#0.0");
        return format.format(f);
    }

    void log(String msg) {
        console.appendText(msg + "\n");
    }

}