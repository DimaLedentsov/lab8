package io;

import common.io.OutputManager;
import controllers.tools.ObservableResourceFactory;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class OutputterUI implements OutputManager {
    private ObservableResourceFactory resourceFactory;
    public OutputterUI(ObservableResourceFactory rf){
        resourceFactory = rf;
    }
    public void error(String s){
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            //alert.setTitle("error");
            //alert.setHeaderText(s);
            alert.setContentText(resourceFactory.getString(s));
            alert.getDialogPane().setStyle("-fx-font-size: 13");
            alert.setHeaderText(null);
            alert.showAndWait();

        });
    }

    public void info(String s){
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            //alert.setTitle("info");
            //alert.setHeaderText(s);
            alert.setContentText(resourceFactory.getString(s));
            alert.getDialogPane().setStyle("-fx-font-size: 13");
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }

}
