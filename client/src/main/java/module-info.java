module ch.lou.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires ChatProtocol;


    opens ch.lou.client to javafx.fxml;
    exports ch.lou.client;
}