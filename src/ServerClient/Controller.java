package ServerClient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
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
    DataInputStream min;
    DataOutputStream mout;


        public void clickbtn () throws IOException {
            mout.writeUTF("Сервер: "+textField.getText()+"\n");
            textArea.appendText("Сервер: "+textField.getText() + "\n");
            textField.clear();
            textField.getOnAction();
            textField.requestFocus();


        }


        public void clickedmouse () {
            textField.clear();

        }


        public void keypress (javafx.scene.input.KeyEvent keyEvent) throws IOException {

            if (keyEvent.getCode() == KeyCode.ENTER) {
                mout.writeUTF("Сервер: "+textField.getText()+"\n");
                textArea.appendText("Сервер: "+textField.getText() + "\n");
                textField.clear();
                textField.getOnAction();
            }


        }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Thread server = new Thread(() -> {
            try {
                ServerSocket sockets = new ServerSocket(PORT);
                System.out.println("Ожидание клиента(ов)...");

                Socket socket = sockets.accept();
                System.out.println("Клиент(ы) подключен(ы)");

                min = new DataInputStream(socket.getInputStream());
                mout = new DataOutputStream(socket.getOutputStream());
                String data = null;

                while (true) {

                    String str = min.readUTF();

                    if (str.equals("/end")) {
                        System.out.println("Клиент отключился");
                        break;
                    }
                    textArea.appendText(str + "\n");
                    System.out.println("Клиент: " + str + "\n");

                    mout.writeUTF(str);

                }


            } catch (Exception x) {
                x.printStackTrace();
            }

        });
        server.start();



      /*  Thread client = new Thread(() -> {


            try {

                InetAddress ipAddress = InetAddress.getByName(IP_ADDRESS);
                Socket socket = new Socket(ipAddress, PORT);
                min = new DataInputStream(socket.getInputStream());
                mout = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    String str = null;
                    str = min.readUTF();


                    if (str.equals("/end")) {
                        System.out.println("Клиент отключился");
                        break;
                    }

                    //System.out.println("Клиент: " + str);



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

        });
        server.start();
        client.start();*/


    }
}

