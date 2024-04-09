package ch.lou.client;

import ch.lou.client.Library.Implementation.Models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.application.Platform;
public class LoginController {

    @FXML
    private TextField txt_username;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_hostname;

    @FXML
    private TextField txt_port;

    @FXML
    protected void handleSubmit() {
        User user = new User(txt_username.getText(), txt_password.getText(), txt_hostname.getText(), Integer.parseInt(txt_port.getText()));
        System.out.println("before");
        App.chatHandler.doLogin(user);
        System.out.println("After");
        App.CloseCurrentPopUp();
//        Platform.exit();
    }

    @FXML
    public void initialize() {
        txt_port.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txt_port.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        try {
            InetAddress host = InetAddress.getLocalHost();
            txt_hostname.setText(host.getHostAddress());
            txt_port.setText("2002");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
