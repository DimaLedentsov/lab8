package controllers;


//import client.App;
import client.Client;
import controllers.tools.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import main.App;

/**
 * Login window controller.
 */
public class LoginWindowController {
    private final Color CONNECTED_COLOR = Color.GREEN;
    private final Color NOT_CONNECTED_COLOR = Color.RED;
    App app;
    Client client;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private CheckBox registerCheckBox;
    @FXML
    private Label isConnectedLabel;
    @FXML
    private Button signInButton;

    private ObservableResourceFactory resourceFactory;

    /**
     * SignI in button on action.
     */
    @FXML
    private void signInButtonOnAction() {
        client.processAuthentication(usernameField.getText(),
                passwordField.getText(),
                registerCheckBox.isSelected());
        app.setMainWindow();

        if (!client.isConnected()) {
            isConnectedLabel.textProperty().bind(resourceFactory.getStringBinding("NotConnected"));
            isConnectedLabel.setTextFill(NOT_CONNECTED_COLOR);
        } else {
            isConnectedLabel.textProperty().bind(resourceFactory.getStringBinding("Connected"));
            isConnectedLabel.setTextFill(CONNECTED_COLOR);
        }
    }

    /**
     * Bind gui language.
     */
    private void bindGuiLanguage() {
        usernameLabel.textProperty().bind(resourceFactory.getStringBinding("UsernameLabel"));
        passwordLabel.textProperty().bind(resourceFactory.getStringBinding("PasswordLabel"));
        registerCheckBox.textProperty().bind(resourceFactory.getStringBinding("RegisterCheckbox"));
        signInButton.textProperty().bind(resourceFactory.getStringBinding("SignInButton"));
        if (client.isConnected()) {
            isConnectedLabel.textProperty().bind(resourceFactory.getStringBinding("Connected"));
            isConnectedLabel.setTextFill(CONNECTED_COLOR);
        } else {
            isConnectedLabel.textProperty().bind(resourceFactory.getStringBinding("NotConnected"));
            isConnectedLabel.setTextFill(NOT_CONNECTED_COLOR);
        }
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void initLangs(ObservableResourceFactory r) {
        resourceFactory = r;
        bindGuiLanguage();
    }
}