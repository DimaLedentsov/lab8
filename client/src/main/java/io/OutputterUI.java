package io;

import javafx.scene.control.Alert;

public class OutputterUI {
    public void error(String s){
        Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("error");
		//alert.setHeaderText(s);
		alert.setContentText(s);
        alert.setHeaderText(null);
		alert.showAndWait();
    }

    public void info(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("info");
		//alert.setHeaderText(s);
		alert.setContentText(s);
        alert.setHeaderText(null);
		alert.showAndWait();
    }
}
