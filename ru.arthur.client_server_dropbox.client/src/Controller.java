import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.Socket;


public class Controller {
    @FXML
    TextField loginField;

    @FXML
    PasswordField passField;

    @FXML
    AnchorPane rootElement;




    public void tryToLogin() throws Exception {
        try {
            System.out.println("Пытаемся соединиться");
            Socket socket = new Socket("localhost",8080);
            System.out.println("Соединились =");
            new Client(loginField.getText(), passField.getText());
        } catch (IOException e) {
            System.out.println("Соединение не удалось");
        }
        loginField.clear();
        passField.clear();
    }
    public void tryToRegister() throws Exception {
        RegisterWindow registerWindow = new RegisterWindow();
        registerWindow.display();
    }
}
