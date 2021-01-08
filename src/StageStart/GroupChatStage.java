package StageStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GroupChatStage extends Application {
    public static Stage GroupChat=null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GroupChat=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/Group_Chat.fxml"));
        primaryStage.setTitle("群聊");
        primaryStage.setScene(new Scene(root, 666, 533));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String... args) {
        launch(args);
    }
}
