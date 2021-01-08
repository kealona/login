package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateGroupStage extends Application {
    public static Stage Create=null;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Create=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/Creat_group.fxml"));
        primaryStage.setTitle("创建群聊");
        primaryStage.setScene(new Scene(root, 600, 274));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
