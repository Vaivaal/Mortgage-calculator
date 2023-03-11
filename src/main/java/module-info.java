module main.lab2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.lab2 to javafx.fxml;
    exports main.lab2;
}