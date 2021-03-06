
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        new ClientInit().start();
        // Getting loader and a pane for Main Login page.
        // Loader will then give a possibility to get related controller
        // First Scene
        FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("GUI.fxml"));
        Parent firstPane = firstPaneLoader.load();
        Scene firstScene = new Scene(firstPane, 900, 600);

        // Getting loader and a pane for Registration Page
        // Second Scene
        FXMLLoader secondPageLoader = new FXMLLoader(getClass().getResource("REGISTER.fxml"));
        Parent secondPane = secondPageLoader.load();
        Scene secondScene = new Scene(secondPane, 900, 600);


        // Page after Login
        // Third Scene
        FXMLLoader thirdPageLoader = new FXMLLoader(getClass().getResource("Knife.fxml"));
        Parent thirdPane = thirdPageLoader.load();
        Scene thirdScene = new Scene(thirdPane,900,600);



        //Scene switcher

        // Injecting first scene into the controller of the second scene
        REGISTERController secondPaneController = secondPageLoader.getController();
        secondPaneController.setFirstScene(firstScene);

        // Injecting second and third scene into the controller of the first scene
        GUIController firstPaneController =  firstPaneLoader.getController();
        firstPaneController.setSecondScene(secondScene);
        firstPaneController.setThirdScene(thirdScene);

        // Injecting first scene into controller of the third scene
        KnifeController thirdPaneController = thirdPageLoader.getController();
        thirdPaneController.setFirstScene(firstScene);

        // Set title
        primaryStage.setTitle("Main menu");
        // Set primary Scene
        primaryStage.setScene(firstScene);
        // Show Scene
        primaryStage.show();

    }


    // Launch app
    public static void main(String[] args) {
        launch(args);
    }





}