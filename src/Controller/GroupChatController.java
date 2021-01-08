package Controller;

import Dao.User;
import Dao.user_getFriendList;
import pojo.ChangeToImage;
import pojo.Message;
import pojo.Message_image;
import pojo.friend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GroupChatController implements Initializable {
    public static friend groupChat = null;
    public static User user_group = null;
    public static GroupChatController GroupChatWindow;

    @FXML
    private ListView groupFriendList;
    @FXML
    private Label group_Name;
    @FXML
    private ListView groupChatList;
    @FXML
    private TextArea Gmsg;

   private ObservableList<friend> groupObservableList = FXCollections.observableArrayList();

   private ObservableList<Message> groupMsgObservableList = FXCollections.observableArrayList();

    /**
     * 初始化群友列表
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GroupChatWindow = this;
        //群名
        group_Name.setText(groupChat.friend_remark);

        try {
            groupObservableList = new user_getFriendList().getFriendList(groupChat.friend_account,3);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        groupFriendList.setCellFactory(new Callback<ListView, ListCell>(){
            @Override
            public ListCell<friend> call(ListView param){
                ListCell<friend> listcell = new ListCell<friend>() {
                    protected void updateItem(friend item, boolean empty) {
                        super.updateItem(item, empty);
                        //生成群成员列表
                        if(!empty){
                            HBox box1 = new HBox(50);
                            box1.setPrefHeight(25.0);
                            Label l1 = new Label();
                            ImageView iv = null;
                            iv = new ImageView(new Image(item.friend_head));
                            iv.setFitHeight(20.0);
                            iv.setFitWidth(20.0);
                            Circle head = new Circle();
                            head.setCenterX(10.0);
                            head.setCenterY(10.0);
                            head.setRadius(10.0);
                            iv.setClip(head);
                            l1.setText(item.friend_nickname);
                            box1.getChildren().addAll(iv,l1);
                            setGraphic(box1);
                            box1.setOnMouseClicked(new EventHandler() {
                                @Override
                                public void handle(Event event) {
                                    //点击好友头像得到好友
                                    FriendPageController.friendInformation = item;
                                    //查询双方是否已经是好友
                                    try {
                                        if(ApplyController.checkFriend(user_group.getAccount(),groupChat.myAccount)){
                                            FriendPageController.mark = 1;
                                        }
                                        else
                                        {
                                            FriendPageController.mark = 0;
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }

                                    StageStart.FriendPageStage friendPageStage=new StageStart.FriendPageStage();
                                    try {
                                        friendPageStage.start(new Stage());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        else{
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
                return listcell;
            }
        });
        groupFriendList.setItems(groupObservableList);

        groupChatList.setCellFactory(new Callback<ListView, ListCell>() {
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
                            ImageView iv = new ImageView(new Image("file:///C:/Users/HP/IdeaProjects/login/src/head_image/new_head.jpg", 40, 40, false, false));

                            Circle circle1 = new Circle();
                            circle1.setRadius(20);
                            circle1.setCenterX(20);
                            circle1.setCenterY(20);
                            iv.setClip(circle1);


                            //发送消息
                            if(item.format == 3) {
                                Label label = new Label(item.sender.getNickname() + "  " + item.time + " \n" + item.wordMsg);
                                System.out.println(item.sender.getNickname() + "  " + item.time + " \n" + item.wordMsg);
                                label.setFont(new Font(16));
                                label.setWrapText(true);
                                vbox.getChildren().addAll(label, image);
                            }
                            else if(item.format == 23){
                                Label label = new Label(item.sender.getNickname() + "  " + item.time + " \n");
                                Image image1 = new Image(item.wordMsg);
                                ImageView image_msg = new ImageView();
                                image_msg.setFitWidth(200);
                                image_msg.setFitHeight(200);
                                image_msg.setImage(image1);
                                vbox.getChildren().addAll(label, image_msg, image);
                            }
                            else if (item.format == 24 ) {
                                Label label = new Label(item.sender.getNickname() + "  " + item.time + " \n" + item.fileData.getName());
                                label.setFont(new Font(20));
                                label.setUnderline(true);
                                //字体颜色改变
                                label.setWrapText(true);
                                vbox.getChildren().addAll(label, image);//想一下怎么在fx上点击listview里的listcell直接打开文件
                            }

                            if (item.sender.getAccount().equals(LoginController.login_user.getAccount())) {
                                hbox.getChildren().addAll(vbox, iv);
                                hbox.setAlignment(Pos.CENTER_RIGHT);
                            } else {
                                ImageView iv2 = new ImageView(new Image("file:///C:/Users/HP/IdeaProjects/login/src/head_image/new_head.jpg",40,40,false,false));
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
        groupChatList.setItems(groupMsgObservableList);
    }

    /**
     * 点击发送按钮
     */
    public void clickSend(){
         String time = ChatController.getTime();
         Message msg = new Message(3,LoginController.login_user,groupChat,
                 Gmsg.getText(),time);
         groupMsgObservableList.add(msg);
         new LoginController().client_login.getSend().send(msg);
         Gmsg.clear();
    }

    /**
     * 接受群消息
     * @param message 接收到的群消息内容
     */
    public void gMsgReceive(Message message){
        String C1 = message.sender.getAccount();
        if (message.format == 3) {
            message.sender = message.group_send;
            //message.group_send = null;
        }
        else if (message.format == 23 || message.format == 24) {
            message.image = new ChangeToImage().toImageData(message.fileData.getContent(), message.fileData.getName());//获取图片,获取文件
            message.sender = message.group_send;
            //message.group_send = null;
        }
        if (groupChat.friend_account.equals(C1) && groupChat != null) {
            groupMsgObservableList.add(message);
        }
    }

    /**
     * 点击图片按钮，发送图片
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
            String time = ChatController.getTime();
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] buf = new byte[fis.available()];
                fis.read(buf);//图片转为二进制文件存储在字节数组中
                fis.close();
                Message image_msg = new Message(23,LoginController.login_user,
                        groupChat, file.getAbsolutePath(), time, file.getName(), null);
                groupMsgObservableList.add(image_msg);
                image_msg.fileData = new Message_image(file.getName(), buf);
                new LoginController().client_login.getSend().send(image_msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 点击文件按钮，发送文件
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
            String time = ChatController.getTime();
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] buf = new byte[fis.available()];
                fis.read(buf);//文件转为二进制文件存储在字节数组中
                fis.close();
                Message file_msg = new Message(24, LoginController.login_user,
                        groupChat, file.getAbsolutePath(), time, file.getName(), null);
                groupMsgObservableList.add(file_msg);
                file_msg.fileData = new Message_image(file.getName(), buf);
                new LoginController().client_login.getSend().send(file_msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 点击邀请群友的按钮
     * @param event 鼠标点击事件
     * @throws Exception 抛出异常
     */
    @FXML
    public void clickAdd(MouseEvent event) throws Exception {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            StageStart.PersonalStage personalStage = new StageStart.PersonalStage();
            personalStage.start(new Stage());
        }
    }
}
