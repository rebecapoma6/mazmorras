module com.poma {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.poma to javafx.fxml;
    exports com.poma;
}
