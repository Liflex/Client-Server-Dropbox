import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIController {

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


    public void openThirdScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(thirdScene);
        System.out.println("Login button was pressed");

    }


    public void passShit() {
        ClientInit.abstractMessage = new AuthMessage("Liflex", "gena9989");

    }
}