package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplyAddStage extends Application {
    public static Stage ADD=null;
    @Override
    public void start(Stage primaryStage) throws IOException {
        ADD=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/ApplyAdd.fxml"));
        primaryStage.setTitle("添加好友");
        primaryStage.setScene(new Scene(root, 422, 262));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
