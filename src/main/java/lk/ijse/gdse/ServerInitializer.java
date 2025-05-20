package lk.ijse.gdse;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class ServerInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Server.fxml"))));
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Server");


        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(stage);
        primaryStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Client2.fxml")))));
        primaryStage.setTitle("EChat");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);

        stage.show();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
