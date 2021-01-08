package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterStage extends Application {
    public static Stage REGISTER=null;
    @Override
    public void start(Stage primaryStage) throws IOException {
        REGISTER=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/Register.fxml"));
        primaryStage.setTitle("Register");
        primaryStage.setScene(new Scene(root, 600, 551));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
