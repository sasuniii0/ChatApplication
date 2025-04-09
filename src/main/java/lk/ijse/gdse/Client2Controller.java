package lk.ijse.gdse;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client2Controller {

    @FXML
    private Button EnterBtnClient;

    @FXML
    private TextField InputFieldClient;

    @FXML
    private TextArea TxtAreaClient;

    @FXML
    private AnchorPane root;

    private DataOutputStream out;
    private DataInputStream in;

    public void Initialize() {
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
    void EnterClientBtnOnAction(ActionEvent event) {
        String message = InputFieldClient.getText();
        if (message.isEmpty() || out == null) return;

        try {
            out.writeUTF(message);
            out.flush();
            TxtAreaClient.appendText("Client2: " + message + "\n");
            InputFieldClient.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
