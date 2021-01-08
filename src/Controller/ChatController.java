package Controller;

import Dao.User;
import pojo.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    public static ChatController CHATWINDOW;

    public static friend friend_pitch = null;
    public static User user_pitch = null;

    @FXML
    private ListView chatList;
    @FXML
    private TextArea msg;
    @FXML
    private Label friend_remark;
    @FXML
    private ChoiceBox commonWords;
    @FXML
    private ListView msgInformationList;
    @FXML
    private Button ComeBack;
    @FXML
    private Pane msgPane;

    private ObservableList<Message> obslist = FXCollections.observableArrayList();
    private ObservableList<Message> msgObsList = FXCollections.observableArrayList();

    /**
     * 发送消息
     * @throws IOException 抛出IO异常
     */
    public void msgsend() throws IOException {
        String time = getTime();
        Message msg_send;
        msg_send = new Message(1,LoginController.login_user,
                friend_pitch, msg.getText(), time);
        obslist.add(msg_send);
        new LoginController().client_login.getSend().send(msg_send);
        System.out.println("消息发送");
        msg.clear();
    }

    /**
     * 接受消息
     * @param message 接受到的消息内容
     */
    public void msreceive(Message message){
        String C1 = message.sender.getAccount();
        if (message.format == 1) {
            if (friend_pitch != null && friend_pitch.friend_account.equals(message.sender.getAccount())) {
                //如果账号不为空，且朋友账号等于发送信息人的账号
                System.out.println("接收者的消息");
            }
        }
        else if (message.format == 2) {
            message.image = new ChangeToImage().toImageData(message.fileData.getContent(), message.fileData.getName());//获取图片路径
        }
        if (friend_pitch.friend_account.equals(C1) && friend_pitch != null) {
            obslist.add(message);
        }
    }

    /**
     * 获取当前时间
     * @return 返回当前时间
     */
    public static String getTime()
    {
        Date TimeOfNow = new Date();
        SimpleDateFormat toString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time;
        time = toString.format(TimeOfNow);
        System.out.println(time);
        return time;
    }

    /**
     * 快捷回复内容
     */
    final String[] word = new String[]{"我在开会不方便，一会儿回复你", "好了，好了，我知道了","是吗","不会是真的吧？","噢"};
    //聊天列表

    /**
     * 初始化聊天界面
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CHATWINDOW = this;

        //常用语
        commonWords.setItems(FXCollections.observableArrayList("我在开会不方便，一会儿回复你", "好了，好了，我知道了", "是吗", "不会是真的吧？", "噢"));
        commonWords.getSelectionModel().selectFirst();//默认选中第一个选项

        //常用语选中
        commonWords.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                msg.appendText(word[newValue.intValue()]);
            }

        });

        //好友备注
        friend_remark.setText(friend_pitch.friend_remark);


        chatList.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell<Message> call(ListView param) {
                ListCell<Message> listcell = new ListCell<Message>() {
                    protected void updateItem(Message item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            HBox hbox = new HBox(20);//水平布局
                            VBox vbox = new VBox(1);//垂直布局
                            hbox.setStyle("-fx-background-color: transparent");
                            vbox.setStyle("-fx-background-color: transparent");
                            //显示消息时间和内容

                            ImageView image = new ImageView();//图片
                            ImageView iv = new ImageView(new Image(user_pitch.getPhoto(), 40, 40, false, false));

                            Circle circle1 = new Circle();
                            circle1.setRadius(20);
                            circle1.setCenterX(20);
                            circle1.setCenterY(20);
                            DropShadow dropshadow = new DropShadow();// 阴影向外
                            dropshadow.setRadius(10);// 颜色蔓延的距离
                            dropshadow.setOffsetX(0);// 水平方向，0则向左右两侧，正则向右，负则向左
                            dropshadow.setOffsetY(0);// 垂直方向，0则向上下两侧，正则向下，负则向上
                            dropshadow.setSpread(0.1);// 颜色变淡的程度
                            dropshadow.setColor(Color.BLACK);// 设置颜色
                            circle1.setEffect(dropshadow);
                            iv.setClip(circle1);


                            //发送消息
                            if (item.format == 1) {
                                Label label = new Label(item.sender.getNickname() + "  " + item.time + " \n" + item.wordMsg);
                                label.setFont(new Font(16));
                                label.setWrapText(true);
                                vbox.getChildren().addAll(label, image);
                            } else if (item.format == 2) {
                                Label label = new Label(item.sender.getNickname() + "  " + item.time + " \n");
                                Image image1 = new Image(item.wordMsg);
                                ImageView image_msg = new ImageView();
                                image_msg.setFitWidth(200);
                                image_msg.setFitHeight(200);
                                image_msg.setImage(image1);
                                vbox.getChildren().addAll(label, image_msg, image);
                                image_msg.setOnMouseClicked(new EventHandler() {
                                    @Override
                                    public void handle(Event event) {
                                        try {
                                            File_open(item.image);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (item.format == 4) {
                                Label label = new Label(item.sender.getNickname() + "  " + item.time + " \n" + item.fileData.getName());
                                label.setFont(new Font(20));
                                label.setUnderline(true);
                                //字体颜色改变
                                label.setWrapText(true);
                                vbox.getChildren().addAll(label, image);//想一下怎么在fx上点击listview里的listcell直接打开文件
                                label.setOnMouseClicked(new EventHandler() {
                                    @Override
                                    public void handle(Event event) {
                                        try {
                                            File_open(item.image);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            if (item.sender.getAccount().equals(LoginController.login_user.getAccount())) {
                                ImageView iv1 = new ImageView(new Image(user_pitch.getPhoto(),40,40,false,false));
                                Circle circle2 = new Circle();
                                circle2.setRadius(20);
                                circle2.setCenterX(20);
                                circle2.setCenterY(20);
                                iv1.setClip(circle2);
                                hbox.getChildren().addAll(vbox, iv1);
                                hbox.setAlignment(Pos.CENTER_RIGHT);
                            } else {
                                ImageView iv2 = new ImageView(new Image(friend_pitch.friend_head, 40, 40, false, false));
                                Circle circle2 = new Circle();
                                circle2.setRadius(20);
                                circle2.setCenterX(20);
                                circle2.setCenterY(20);
                                iv2.setClip(circle2);
                                hbox.getChildren().addAll(iv2, vbox);
                                hbox.setAlignment(Pos.CENTER_LEFT);
                            }
                            this.setGraphic(hbox);
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
                listcell.setStyle("-fx-border-radius: 5px; -fx-background-radius: 5px;-fx-background-color: transparent");
                return listcell;
            }

        });
        chatList.setItems(obslist);

        try {
            msgObsList = new user_getMsg().getMsgList(user_pitch.getAccount(), friend_pitch.friend_account);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        msgInformationList.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell<Message> call(ListView param) {
                ListCell<Message> listcell = new ListCell<Message>() {
                    protected void updateItem(Message item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            HBox hbox = new HBox(20);//水平布局
                            VBox vbox = new VBox(1);//垂直布局
                            //显示消息时间和内容

                            ImageView image = new ImageView();//图片
                            ImageView iv = new ImageView(new Image(user_pitch.getPhoto(), 40, 40, false, false));

                            Circle circle1 = new Circle();
                            circle1.setRadius(20);
                            circle1.setCenterX(20);
                            circle1.setCenterY(20);
                            iv.setClip(circle1);


                            //发送消息
                            if (item.format == 1) {
                                Label label = new Label(item.sender.getNickname() + "  " + item.time + " \n" + item.wordMsg);
                                System.out.println(item.sender.getNickname() + "  " + item.time + " \n" + item.wordMsg);
                                label.setFont(new Font(16));
                                label.setWrapText(true);
                                vbox.getChildren().addAll(label, image);
                            } else if (item.format == 2) {
                                Label label = new Label(item.sender.getNickname() + "  " + item.time + " \n");
                                Image image1 = new Image(item.wordMsg);
                                ImageView image_msg = new ImageView();
                                image_msg.setFitWidth(200);
                                image_msg.setFitHeight(200);
                                image_msg.setImage(image1);
                                vbox.getChildren().addAll(label, image_msg, image);
                                image_msg.setOnMouseClicked(new EventHandler() {
                                    @Override
                                    public void handle(Event event) {
                                        try {
                                            File_open(item.image);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (item.format == 4) {
                                Label label = new Label(item.sender.getNickname() + "  " + item.time + " \n" + item.fileData.getName());
                                label.setFont(new Font(20));
                                label.setUnderline(true);
                                //字体颜色改变
                                label.setWrapText(true);
                                vbox.getChildren().addAll(label, image);
                                label.setOnMouseClicked(new EventHandler() {
                                    @Override
                                    public void handle(Event event) {
                                        try {
                                            File_open(item.image);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            if (item.sender.getAccount().equals(LoginController.login_user.getAccount())) {
                                hbox.getChildren().addAll(vbox, iv);
                                hbox.setAlignment(Pos.CENTER_RIGHT);
                            } else {
                                ImageView iv2 = new ImageView(new Image(friend_pitch.friend_head, 40, 40, false, false));
                                Circle circle2 = new Circle();
                                circle2.setRadius(20);
                                circle2.setCenterX(20);
                                circle2.setCenterY(20);
                                iv2.setClip(circle2);
                                hbox.getChildren().addAll(iv2, vbox);
                                hbox.setAlignment(Pos.CENTER_LEFT);
                            }
                            this.setGraphic(hbox);
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
                listcell.setStyle("-fx-background-color: #f5f0f0; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                return listcell;
            }

        });
        msgInformationList.setItems(msgObsList);
    }

    /**
     * 点击发送图片按钮
     */
    public void clickPhoto(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Photo Files"
                , "*.jpg", "*.png", "*.bmp", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            //转换文件
            String time = getTime();
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] buf = new byte[fis.available()];
                fis.read(buf);//把图片转为二进制文件存储在字节数组中
                fis.close();
                Message image_msg = new Message(2,LoginController.login_user,
                        friend_pitch, file.getAbsolutePath(), time, file.getName(), null);
                obslist.add(image_msg);
                image_msg.fileData = new Message_image(file.getName(), buf);
                new LoginController().client_login.getSend().send(image_msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 点击发送文件按钮
     */
    public void clickFile(){
        //选择文件
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择需要的打开的文件");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("所有文件"
                , "*"));
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog(new Stage());
        //转换文件
        if (file != null) {
            String time = getTime();
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] buf = new byte[fis.available()];
                fis.read(buf);//文件转为二进制文件存储在字节数组中
                fis.close();
                Message file_msg = new Message(4, LoginController.login_user,
                        friend_pitch, file.getAbsolutePath(), time, file.getName(), null);
                obslist.add(file_msg);
                //Todo
                if (friend_pitch.friend_account.length() == 6) {
                    file_msg.format = 24;
                }
                file_msg.fileData = new Message_image(file.getName(), buf);
                new LoginController().client_login.getSend().send(file_msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 点击打开文件
     * @param path 文件的地址
     * @throws IOException 抛出IO异常
     */
    public void File_open(String path) throws IOException {
        File file = new File(path);
        Desktop.getDesktop().open(file);
    }

    /**
     * 点击消息列表
     */
    public void clickMsgList(){
        msgPane.setVisible(true);
        ComeBack.setVisible(true);
    }

    /**
     * 点击返回
     */
    public void clickReturn(){
        ComeBack.setVisible(false);
        msgPane.setVisible(false);
    }

    /*public void ButtonShake(ActionEvent event){

    }*/

   /* @FXML
    private GridPane Expression;
    private int Emoji = 0;

    public void clickExpression(){
        if (Emoji == 0) {
            Expression.setVisible(true);
            Emoji = 1;
        } else {
            Expression.setVisible(false);
            Emoji = 0;
        }
    }

    public void Emoji_1() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83D\uDE02");
    }

    public void Emoji_2() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "☺");
    }

    public void Emoji_3() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83C\uDF48");
    }

    public void Emoji_4() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83D\uDCA9");
    }

    public void Emoji_5() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83D\uDE49");
    }

    public void Emoji_6() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83D\uDCA3");
    }

    public void Emoji_7() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83D\uDE0D");
    }

    public void Emoji_8() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83D\uDE31");
    }

    public void Emoji_9() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83D\uDE08");
    }

    public void Emoji_10() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83D\uDE4F");
    }

    public void Emoji_11() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83D\uDC4E");
    }

    public void Emoji_12() {
        Expression.setVisible(false);
        Emoji = 0;
        msg.setText(msg.getText() + "\uD83D\uDC97");
    }*/

}
