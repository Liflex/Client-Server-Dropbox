import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class RegisterWindow {
    public void display() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registerform.fxml"));
        Parent rootregister = fxmlLoader.load();
        Scene registerScene = new Scene(rootregister);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Окно регистрации");
        window.setScene(registerScene);
        window.show();
        window.setResizable(false);
    }
}
