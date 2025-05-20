package lk.ijse.gdse;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientController {

    @FXML
    private Button UploadEmojiBtn;

    @FXML
    private Button EnterBtnClient;

    @FXML
    private TextField InputFieldClient;

    @FXML
    private TextArea TxtAreaClient;

    @FXML
    private AnchorPane root;

    private Socket socket;
    private DataInputStream in;
    private  DataOutputStream out;
    private String clientName = " ";

    private ListView <String> emojiList;

    private ServerController serverController;



    public void initialize(){
        InputFieldClient.setText(clientName);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    socket = new Socket("localhost", 5000);
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    //System.out.println("Client connected");

                    //TxtAreaClient.appendText(clientName + " joined");
                    //out.writeUTF(clientName + "joined\n");

                    if (serverController != null) {
                        Platform.runLater(() -> {
                            if (serverController.TxtAreaServer != null) {
                                serverController.TxtAreaServer.appendText(clientName + " joined\n");
                            }
                        });
                    }

                    while (socket.isConnected()){
                        String receivingMsg = in.readUTF();
                        TxtAreaClient.appendText(receivingMsg);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @FXML
    void EnterClientBtnOnAction(javafx.event.ActionEvent event) {
        String message = InputFieldClient.getText();
        if (message.isEmpty() || out == null) return;

        try {
            out.writeUTF(clientName + "-" + message + "\n");
            out.flush();
            TxtAreaClient.appendText(clientName + " - " + message + "\n");
            InputFieldClient.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputFieldClient.clear();
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


                    // send file content
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = files.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

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

    public void setClientName(String text) {
        clientName = text;
    }

    public void shutdown() throws IOException {
        TxtAreaClient.appendText(clientName + " left the chat ");

        out.writeUTF(clientName + "left the chat\n");
    }

    public void UploadImageOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files" ,"*.png","*.jpg","*.jpeg","*.gif"));
        File file = fileChooser.showOpenDialog(null);

    }

    public void UploadEmojiBtnOnAction(ActionEvent actionEvent) {
        EmojiPicker emojiPicker = new EmojiPicker();
        emojiPicker.showEmojiPicker(InputFieldClient);
    }
}

        /*String[] emojiHtmlList = new String[]{"&#128514;","&#10084;","&#128525;","&#129315;","&#128522;",
                "&#128591;","&#128149;","&#128557;","&#128293;","&#128536;","&#128077;","&#129392;","&#128526;","&#128518;",
                "&#128513;","&#128521;","&#129300;","&#128517;","&#128532;","&#128580;","&#128540;","&#9829;","&#9851;","&#128530;",
                "&#128553;","&#9786;","&#128513;","&#128076;","&#128079;","&#128148;","&#128150;","&#128153;",
                "&#128546;","&#128170;","&#129303;","&#128156;","&#128526;","&#128519;","&#127801;","&#129318;",
                "&#127881;","&#128158;","&#9996;","&#10024;","&#129335;","&#128561;","&#128524;","&#127800;",
                "&#128588;","&#128523;","&#127770;","&#127773;","&#128584;","&#128585;","&#128586;"};


    }


}*/
