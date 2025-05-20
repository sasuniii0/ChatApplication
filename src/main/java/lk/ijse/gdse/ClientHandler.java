package lk.ijse.gdse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler {
    private Socket socket;
    private List<ClientHandler> clients;
    private DataOutputStream outputStream;;
    private DataInputStream inputStream;
    private String msg = "";

    public ClientHandler(Socket socket,List<ClientHandler> clients){
        try{
            this.socket=socket;
            this.clients=clients;
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (socket.isConnected()){
                        msg = inputStream.readUTF();
                        System.out.println(msg);
                        for (ClientHandler client : clients) {
                            if (client.socket.getPort() != socket.getPort()) {
                                client.outputStream.writeUTF(msg);
                                client.outputStream.flush();
                            }
                        }
                    }
                } catch ( IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
