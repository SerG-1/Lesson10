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
    private final String IP_ADDRESS = "192.168.0.10";
    private final int PORT = 8189;

    private Socket socket;
    DataInputStream in;
    DataOutputStream out;


        public void clickbtn () throws IOException {
            out.writeUTF("Сергей: "+textField.getText()+"\n");

            textField.clear();
            textField.getOnAction();
            textField.requestFocus();


        }


        public void clickedmouse () {
            textField.clear();

        }


        public void keypress (javafx.scene.input.KeyEvent keyEvent) throws IOException {

            if (keyEvent.getCode() == KeyCode.ENTER) {
                out.writeUTF("Сергей: "+textField.getText()+"\n");

                textField.clear();
                textField.getOnAction();
            }


        }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(()-> {

                    try {
                        while (true) {
                            String str = null;
                            str = in.readUTF();


                            if (str.equals("/end")) {
                                System.out.println("Клиент отключился");
                                break;
                            }

                            System.out.println("Клиент: " + str);
                            textArea.appendText(str);
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

            }).start();

        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }
}

