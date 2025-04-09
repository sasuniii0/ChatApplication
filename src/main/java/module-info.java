module ChatApplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens lk.ijse.gdse to javafx.fxml;
    exports lk.ijse.gdse;
}