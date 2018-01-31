import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.Socket;


public class Controller {
    @FXML
    TextField loginField;

    @FXML
    PasswordField passField;

    @FXML
    AnchorPane rootElement;




    public void tryToLogin() throws SSLException {
        new Client(loginField.getText(), passField.getText()).run();
        loginField.clear();
        passField.clear();
    }
    public void tryToRegister() throws Exception {
        RegisterWindow registerWindow = new RegisterWindow();
        registerWindow.display();
    }
}
