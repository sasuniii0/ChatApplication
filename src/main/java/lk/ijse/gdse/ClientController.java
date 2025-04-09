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
import java.net.Socket;

public class ClientController {

    @FXML
    private Button EnterBtnClient;

    @FXML
    private TextField InputFieldClient;

    @FXML
    private TextArea TxtAreaClient;

    @FXML
    private AnchorPane root;

    private DataOutputStream out;

    public void initialize() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 5000);
                TxtAreaClient.appendText("Connected to server.\n");

                DataInputStream in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    String message = in.readUTF();
                    if (message.equalsIgnoreCase("bye")) break;

                    Platform.runLater(() -> TxtAreaClient.appendText("Server: " + message + "\n"));
                }

                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void EnterClientBtnOnAction(javafx.event.ActionEvent event) {
        String message = InputFieldClient.getText();
        if (message.isEmpty() || out == null) return;

        try {
            out.writeUTF(message);
            out.flush();
            TxtAreaClient.appendText("Client: " + message + "\n");
            InputFieldClient.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UploadBtnOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            new Thread(() -> {
                try {
                    Socket socket = new Socket("localhost", 5000);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    FileInputStream files = new FileInputStream(file);

                    out.writeUTF(file.getName());

                    out.writeLong(file.length());

                    files.close();
                    out.close();
                    socket.close();
                    System.out.println(" File sent: " + file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
