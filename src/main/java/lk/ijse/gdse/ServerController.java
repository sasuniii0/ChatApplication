package lk.ijse.gdse;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {

    @FXML
    private Button EnterBtnServer;

    @FXML
    private TextField InputFieldServer;

    @FXML
    private TextArea TxtAreaServer;

    @FXML
    private AnchorPane root;

    private DataOutputStream out;

    public void initialize() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(5000);
                Socket socket = serverSocket.accept();
                TxtAreaServer.appendText("Client connected.\n");

                DataInputStream in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    String message = in.readUTF();
                    if (message.equalsIgnoreCase("bye")) break;

                    Platform.runLater(() -> TxtAreaServer.appendText("Client: " + message + "\n"));
                }

                socket.close();
                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void EnterServerBtnOnAction(javafx.event.ActionEvent event) {
        String message = InputFieldServer.getText();
        if (message.isEmpty() || out == null) return;

        try {
            out.writeUTF(message);
            out.flush();
            TxtAreaServer.appendText("Server: " + message + "\n");
            InputFieldServer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UploadBtnOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file!=null){
            new Thread(()->{
                try{
                    FileInputStream fileInputStream = new FileInputStream(file);
                    System.out.println(file.getName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();

        }
    }
}
