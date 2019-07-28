module todos {
    requires javafx.controls;
    requires javafx.fxml;

    opens todos to javafx.fxml;
    exports todos;
}