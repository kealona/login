package StageStart;

import Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FriendPageStage extends Application {

    public static Stage FriendPages=null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FriendPages=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/FriendPage.fxml"));
        primaryStage.setTitle("FriendPages");
        primaryStage.setScene(new Scene(root, 568, 502));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}
