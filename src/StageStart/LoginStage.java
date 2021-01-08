package StageStart;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginStage extends Application{

    public static Stage LOGIN=null;

    double x1;
    double y1;
    double x_stage;
    double y_stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        LOGIN=primaryStage;
        LOGIN.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/Login.fxml"));
        primaryStage.setTitle("Login");
        DropShadow dropshadow = new DropShadow();// 阴影向外
        dropshadow.setRadius(10);// 颜色蔓延的距离
        dropshadow.setOffsetX(0);// 水平方向，0则向左右两侧，正则向右，负则向左
        dropshadow.setOffsetY(0);// 垂直方向，0则向上下两侧，正则向下，负则向上
        dropshadow.setSpread(0.1);// 颜色变淡的程度
        dropshadow.setColor(Color.BLACK);// 设置颜色
        root.setEffect(dropshadow);// 绑定指定窗口控件
        primaryStage.setScene(new Scene(root, 430, 350));
        primaryStage.getScene().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent m) {
                //计算
                primaryStage.setX(x_stage + m.getScreenX() - x1);
                primaryStage.setY(y_stage + m.getScreenY() - y1);
            }
        });
        primaryStage.getScene().setOnDragEntered(null);
        primaryStage.getScene().setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override public void handle(MouseEvent m) {

                //按下鼠标后，记录当前鼠标的坐标
                x1 =m.getScreenX();
                y1 =m.getScreenY();
                x_stage = primaryStage.getX();
                y_stage = primaryStage.getY();

            }
        });
        primaryStage.setResizable(false);

        primaryStage.show();
    }
    public static void main(String... args) {
        launch(args);
    }

}
