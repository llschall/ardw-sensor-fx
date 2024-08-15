package org.ardw.sensor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ardw-sensor.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add("org/ardw/sensor/style.css");

        scene.setOnKeyPressed(evt -> {
            if (evt.isAltDown() &&
                    KeyEvent.VK_ESCAPE == evt.getCode().getCode()
            ) {
                Platform.exit();
                System.exit(0);
            }
        });

        var controller = (Controller) loader.getController();
        controller.start();

        stage.setTitle("Ardw Sensor FX");
        stage.setScene(scene);

        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}