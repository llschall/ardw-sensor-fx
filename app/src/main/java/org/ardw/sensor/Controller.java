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

    @FXML
    AreaChart<Number, Number> chartT, chartH;

    @FXML
    Label labelT, labelH, labelTZ, labelHZ, labelTD, labelHD, loopLbl;

    @FXML
    TextArea console;

    Measure zero = null;

    ObservableList<AreaChart.Data<Number, Number>> dataT, dataH, zeroT, zeroH, deltaT, deltaH;

    LinkedList<Measure> list = new LinkedList<>();

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
            Platform.runLater(() -> {
                labelTZ.setText("" + zero.temperature);
                labelHZ.setText("" + zero.humidity);
            });
        }
        while (!Measure.measures.isEmpty()) {

            Measure measure = Measure.measures.remove();
            list.addFirst(measure);
            while (list.size() > 1000) {
                list.removeLast();
            }

            Measure delta = list.getLast();

            long time = (measure.timeMs - zero.timeMs) / 1000;
            Platform.runLater(() -> {
                loopLbl.setText("#" + count++);

                labelT.setText(format(measure.temperature));
                labelH.setText(format(measure.humidity));

                dataT.add(new AreaChart.Data<>(time, measure.temperature - 2));
                zeroT.add(new AreaChart.Data<>(time, zero.temperature));
                deltaT.add(new AreaChart.Data<>(time, delta.temperature - 1));
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