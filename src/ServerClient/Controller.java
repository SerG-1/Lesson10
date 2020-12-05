package ServerClient;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;


public class Controller {


    public TextArea textArea;
    @FXML
    public TextField textField;
    @FXML
    private final int PORT = 8189;
    public Socket socket;
    static List<Clients> clients;


    public void clickbtn() {
        broadCastMsg("Сервер: " + textField.getText() + "\n");
        //textArea.appendText("Сервер: " + textField.getText() + "\n");
        textField.clear();
        textField.getOnAction();
        textField.requestFocus();


    }


    public void clickedmouse() {
        textField.clear();

    }


    public void keypress(javafx.scene.input.KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            broadCastMsg("Сервер: " + textField.getText() + "\n");
            //textArea.appendText("Сервер: " + textField.getText() + "\n");
            textField.clear();
            textField.getOnAction();
        }


    }


    public void server() {
        clients=new Vector<>();
        new Thread(() -> {



            try {
                ServerSocket server = new ServerSocket(PORT);
                System.out.println("Ожидание клиента(ов)...");

                while (true) {
                    socket = server.accept();

                    System.out.println("Клиент(ы) подключен(ы)" + socket);


                    clients.add(new Clients(socket));


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static void broadCastMsg(String msg) {
        for (Clients client : clients) {
            client.sendMsg(msg + "\n");
        }
    }



}

