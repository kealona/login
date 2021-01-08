package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ChatInterfaceStage extends Application{

    public static Stage CHATINTERFACE=null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        CHATINTERFACE=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/ChatInterface.fxml"));
        primaryStage.setTitle("ChatInterface");
        primaryStage.setScene(new Scene(root, 604, 570));
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    public static void main(String... args) {
        launch(args);
    }

}