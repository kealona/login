package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplyStage extends Application {
    public static Stage ADD=null;
    @Override
    public void start(Stage primaryStage) throws IOException {
        ADD=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/Apply.fxml"));
        primaryStage.setTitle("Apply");
        primaryStage.setScene(new Scene(root, 485, 310));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
