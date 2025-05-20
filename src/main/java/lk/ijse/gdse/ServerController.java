package lk.ijse.gdse;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
    TextArea TxtAreaServer ;

    @FXML
    private AnchorPane root;

    private ServerHandler serverHandler;

    private DataOutputStream output;


    public void initialize() {
        new Thread(() -> {
            try {
                serverHandler = ServerHandler.getInstance();
                serverHandler.makeSocket();
                TxtAreaServer.appendText("Client connected.\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void EnterServerBtnOnAction(javafx.event.ActionEvent event) {
        String message = InputFieldServer.getText();
        if (message.isEmpty() || output == null) return;

        try {
            output.writeUTF(message);
            output.flush();
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

    public void AddBtnOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(root.getScene().getWindow());
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Client2.fxml"));
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong. Can't add client.").show();
        }
        stage.setTitle("Add Client");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
