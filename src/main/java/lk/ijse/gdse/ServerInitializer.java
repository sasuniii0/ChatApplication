package lk.ijse.gdse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ServerInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Server.fxml")));
        stage.setScene(new Scene(load));
        stage.setTitle("Server");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
