package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountStage extends Application {

    public static Stage ACCOUNT=null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ACCOUNT=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/Account.fxml"));
        primaryStage.setTitle("GET ACCOUNT");
        primaryStage.setScene(new Scene(root, 420, 301));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}
