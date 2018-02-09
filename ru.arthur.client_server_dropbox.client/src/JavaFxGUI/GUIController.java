import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class GUIController {

    public PasswordField login;
    public PasswordField password;
    private Scene secondScene;
    private Scene thirdScene;

    public void setSecondScene(Scene scene) {
        secondScene = scene;
    }

    public void setThirdScene(Scene scene) {
        thirdScene = scene;

    }

    public void openSecondScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
    }


    public void openThirdScene(ActionEvent actionEvent) throws Exception {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(thirdScene);
        ClientInit.abstractMessage = new AuthMessage(login.getText(), password.getText());
        new ClientInit().start();
        System.out.println("Login button was pressed");

    }


    public void passShit() {

    }
}