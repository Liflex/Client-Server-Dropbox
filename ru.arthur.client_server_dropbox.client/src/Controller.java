import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;


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
            Socket socket = new Socket("localhost",5555);
            System.out.println("Соединились =");
            new SocketThread("SocketThread", socket, loginField.getText(), passField.getText());
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
