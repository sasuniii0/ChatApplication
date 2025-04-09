package lk.ijse.gdse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Client2Initializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Client2.fxml")));
        stage.setScene(new Scene(load));
        stage.setTitle("Client2");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
