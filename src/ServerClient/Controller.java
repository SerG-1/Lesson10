package ServerClient;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class Controller{

    @FXML
    public javafx.scene.control.TextArea textArea;
    @FXML
    public TextField textField;
    private final int PORT = 8189;
    public Socket socket;
    List<Clients> clients;

public Controller() {

    new Thread(()-> {
        clients = new Vector<>();

        try{
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Ожидание клиента(ов)...");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент(ы) подключен(ы)" + socket);
                clients.add(new Clients(socket,this));

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }).start();
}



    public void clickbtn() throws IOException {
      broadCastMsg("Сервер: " + textField.getText());
        textField.clear();
        textField.getOnAction();
        textField.requestFocus();
    }
    public void clickedmouse() {
        textField.clear();

    }
    public void keypress(javafx.scene.input.KeyEvent keyEvent) throws IOException {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            broadCastMsg("Сервер: " + textField.getText());

            textField.clear();
            textField.getOnAction();

        }
    }
    public void broadCastMsg(String msg) throws IOException {

        for (Clients client : clients) {

            client.sendMsg(msg);

        }
    }

}