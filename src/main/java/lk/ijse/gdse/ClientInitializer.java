package lk.ijse.gdse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class ClientInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        /*Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/view/Client.fxml")));

        stage.setScene(new Scene(root));
        stage.setTitle("EChat");
        stage.centerOnScreen();
        stage.setResizable(false);

        Stage prStage = new Stage();
        prStage.initModality(Modality.WINDOW_MODAL);
        prStage.initOwner(stage);
        prStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Client2.fxml")))));
        prStage.setTitle("Secondary Window");
        prStage.centerOnScreen();
        prStage.setResizable(false);

        stage.show();*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
