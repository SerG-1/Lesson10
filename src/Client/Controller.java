package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public javafx.scene.control.TextArea textArea;
    @FXML
    public TextField textField;
    @FXML
    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8189;

    private Socket socket;
    DataInputStream in;
    DataOutputStream out;



    public void clickbtn() {
        try {
        out.writeUTF(textField.getText());
        out.flush();
        textField.clear();
        textField.getOnAction();
        textField.requestFocus();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }




    public void clickedmouse() {
        textField.clear();

    }





    public void keypress(javafx.scene.input.KeyEvent keyEvent) {
        try {
            if(keyEvent.getCode() == KeyCode.ENTER){
            out.writeUTF(textField.getText());
            textField.clear();
            textField.getOnAction();}}
        catch (IOException e){
            e.printStackTrace();
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ServerSocket sockets = new ServerSocket(PORT);
            System.out.println("Ожидание клиента(ов)...");

            Socket socket = sockets.accept();
            System.out.println("Клиент(ы) подключен(ы)");

            //socket = new Socket(IP_ADDRESS, PORT);


            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = null;
                            str = in.readUTF();


                            if (str.equals("/end")) {
                                System.out.println("Клиент отключился");
                                break;
                            }

                            System.out.println("Клиент: " + str);
                            textArea.appendText("Сергей: "+str+"\n");




                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Мы отключились от сервера");
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }
}