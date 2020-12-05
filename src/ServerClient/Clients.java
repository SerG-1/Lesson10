package ServerClient;

import com.sun.security.ntlm.Server;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Clients extends Thread {

    public TextArea textArea;
    @FXML

    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;


    public Clients(Socket socket) {
        try {

            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());


            new Thread(() -> {
                try {
                    Controller c = new Controller();

                    while (true) {
                        String str=null;

                            str = in.readUTF();




                        if (str.equals("/end")) {
                            System.out.println("Клиент отключился");
                            break;
                        }

                        System.out.println(str);


                        Controller.broadCastMsg(str);

                       textArea.appendText(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
