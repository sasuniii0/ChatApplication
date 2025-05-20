package lk.ijse.gdse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button EnterBtnLogIn;

    @FXML
    private TextField InputFieldName;

    @FXML
    private AnchorPane root;

    @FXML
    void EnterlogInBtnOnAction(ActionEvent event) throws IOException {
        if (!InputFieldName.getText().isEmpty()) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Client.fxml"));

            Parent root = loader.load();

            // Get the controller that was created by FXML
            ClientController controller = loader.getController();
            controller.setClientName(InputFieldName.getText());

            stage.setScene(new Scene(root));
            stage.setTitle(InputFieldName.getText());
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setOnCloseRequest(windowEvent -> {
                try {
                    controller.shutdown();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            stage.show();
            InputFieldName.clear();
        } else {
            System.out.println("Please enter a valid name");
        }
    }

}
