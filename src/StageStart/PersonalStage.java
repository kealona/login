package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PersonalStage extends Application{

    public static Stage Personal=null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Personal=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/Personal.fxml"));
        primaryStage.setTitle("personal information");
        primaryStage.setScene(new Scene(root, 663, 543));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String... args) {
        launch(args);
    }

}