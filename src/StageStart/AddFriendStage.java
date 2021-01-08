package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddFriendStage extends Application {
    public static Stage ADD=null;
    @Override
    public void start(Stage primaryStage) throws IOException {
        ADD=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/AddFriend.fxml"));
        primaryStage.setTitle("搜索");
        primaryStage.setScene(new Scene(root, 600, 198));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
