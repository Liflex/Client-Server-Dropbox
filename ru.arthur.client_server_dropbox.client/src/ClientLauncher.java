import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ClientLauncher extends Application{

    static Stage window;
    private Scene home;
    private Scene home1;
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - ");

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        window.setTitle("Сетевое хранилище");
        home = new Scene(root, 1000, 500);
        window.setScene(home);
        window.show();
        window.setResizable(false);
        window.setOnCloseRequest(event -> disconnect());
    }



    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private void disconnect()
    {
        window.close();
        System.out.println("Дисконнект");
    }
}

