package io;

import common.io.OutputManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class OutputterUI implements OutputManager {
    public void error(String s){
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            //alert.setHeaderText(s);
            alert.setContentText(s);
            alert.setHeaderText(null);
            alert.showAndWait();

        });
    }

    public void info(String s){
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("info");
            //alert.setHeaderText(s);
            alert.setContentText(s);
            alert.setHeaderText(null);
            alert.showAndWait();
        });

    }
}
