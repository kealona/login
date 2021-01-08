package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ForgetStage extends Application{

    public static Stage FORGET=null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FORGET=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/Forget.fxml"));
        primaryStage.setTitle("Forget-password");
        primaryStage.setScene(new Scene(root, 600, 575));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String... args) {
        launch(args);
    }

}