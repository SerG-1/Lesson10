package ServerClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Clients {

    DataOutputStream out;
    DataInputStream in;
    public Socket socket;

    public Clients(Socket socket,Controller controller) throws IOException {
       this.socket = socket;

       in = new DataInputStream(controller.socket.getInputStream());
     
        new Thread(()-> {

            while (true) {
                try {
                    String str;
                    str = in.readUTF();
                    System.out.println(str);
                    controller.textArea.appendText(str);
                    controller.broadCastMsg(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    void sendMsg (String msg) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(msg);
    }

}