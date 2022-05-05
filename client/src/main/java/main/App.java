package main;


import java.io.PrintStream;

import client.Client;
import common.exceptions.*;
import controllers.LoginWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.tools.*;
import static common.io.OutputManager.*;


public class App extends Application {
    //public static Logger logger = LogManager.getLogger("logger");
    //static final Logger logger = LogManager.getRootLogger();
    private static final String APP_TITLE = "Collection Keeper";
    Client client;
    String address;
    int port;
    private static ObservableResourceFactory resourceFactory;
    private Stage primaryStage;
    private void initialize(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        print("\r\n __          __           _                 __  __                                         \r\n \\ \\        / /          | |               |  \\/  |                                        \r\n  \\ \\  /\\  / /___   _ __ | | __ ___  _ __  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __ \r\n   \\ \\/  \\/ // _ \\ | '__|| |/ // _ \\| '__| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|\r\n    \\  /\\  /| (_) || |   |   <|  __/| |    | |  | || (_| || | | || (_| || (_| ||  __/| |   \r\n     \\/  \\/  \\___/ |_|   |_|\\_\\\\___||_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|   \r\n                                                                          __/ |            \r\n                                                                         |___/             \r\n");
        print("\t\t\t\t\t\t\t\t\t by Dimka Ledentsov");
        print("\n");

        address = "localhost";
        String strPort = "4445";
        port = 0;
        try {
            if (args.length == 2) {
                address = args[0];
                strPort = args[1];
            }
            if(args.length == 1){
                strPort = args[0];
                print("no address passed by arguments, setting default : " + address);
            }
            if(args.length == 0){
                print("no port and no address passed by arguments, setting default :" + address + "/" + strPort);
            }
            try {
                port = Integer.parseInt(strPort);
            } catch (NumberFormatException e) {
                throw new InvalidPortException();
            }

        } catch (ConnectionException e) {
            print(e.getMessage());
        }
    }

    @Override
    public void start(Stage stage) {
        try {
            this.primaryStage = stage;

            FXMLLoader loginWindowLoader = new FXMLLoader();
            loginWindowLoader.setLocation(getClass().getResource("/view/LoginWindow.fxml"));
            Parent loginWindowRootNode = loginWindowLoader.load();
            Scene loginWindowScene = new Scene(loginWindowRootNode);
            LoginWindowController loginWindowController = loginWindowLoader.getController();
            loginWindowController.setApp(this);
            loginWindowController.setClient(client);
            //loginWindowController.initLangs(resourceFactory);

            primaryStage.setTitle(APP_TITLE);

            primaryStage.setScene(loginWindowScene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception exception) {
            // TODO: Обработать ошибки
            System.out.println(exception);
            exception.printStackTrace();
        }
    }
}
