package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainInterfaceStage extends Application {

    public static Stage Interface=null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Interface=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/MainInterface.fxml"));
        primaryStage.setTitle("MainInterface");
        primaryStage.setScene(new Scene(root, 306, 620));
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    public static void main(String[] args){launch(args);}

}
