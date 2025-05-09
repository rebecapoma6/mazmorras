module com.poma {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.poma to javafx.fxml;
    opens com.poma.controller to javafx.fxml;
    exports com.poma;
}
