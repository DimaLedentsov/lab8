package main;


import java.io.PrintStream;

import client.Client;
import common.exceptions.*;

import static common.io.OutputManager.*;


public class Main {
    //public static Logger logger = LogManager.getLogger("logger");
    //static final Logger logger = LogManager.getRootLogger();
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        print("\r\n __          __           _                 __  __                                         \r\n \\ \\        / /          | |               |  \\/  |                                        \r\n  \\ \\  /\\  / /___   _ __ | | __ ___  _ __  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __ \r\n   \\ \\/  \\/ // _ \\ | '__|| |/ // _ \\| '__| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|\r\n    \\  /\\  /| (_) || |   |   <|  __/| |    | |  | || (_| || | | || (_| || (_| ||  __/| |   \r\n     \\/  \\/  \\___/ |_|   |_|\\_\\\\___||_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|   \r\n                                                                          __/ |            \r\n                                                                         |___/             \r\n");
        print("\t\t\t\t\t\t\t\t\t by Dimka Ledentsov");
        print("\n");

        String address = "localhost";
        String strPort = "4445";
        int port = 0;
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
            Client client = new Client(address, port);
            try {
                client.start();
            } catch (EndOfInputException e) {
                printErr(e.getMessage());
            }
        } catch (ConnectionException e) {
            print(e.getMessage());
        }
    }
}
