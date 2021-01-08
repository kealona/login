package Controller;

import Dao.*;
import pojo.Apply_msg;
import pojo.friend;
import pojo.user_getApply;
import Driver.MySqlDriver;
import StageStart.ApplyAddStage;
import StageStart.FriendPageStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.util.Callback;


public class MainInterfaceController implements Initializable{
    @FXML
    private ListView friendList;
    @FXML
    private ListView msgList;
    @FXML
    private ListView newFriendList;
    @FXML
    private ImageView head;
    @FXML
    private Label user_name;
    @FXML
    private Label user_label;
    @FXML
    private ListView groupList;
    @FXML
    private ListView newGroupList;


    private ObservableList<friend> friendObsList = FXCollections.observableArrayList();

    private ObservableList<Apply_msg> newFriendObsList = FXCollections.observableArrayList();

    private ObservableList<friend> groupObsList = FXCollections.observableArrayList();

    private ObservableList<Apply_msg> newGroupObsList = FXCollections.observableArrayList();


    //搜索好友
    @FXML
    public void searchInformation(MouseEvent event) throws Exception{
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1){
            StageStart.AddFriendStage addFriendStage=new StageStart.AddFriendStage();
            addFriendStage.start(new Stage());
        }
    }

    //点击刷新信息
    public void clickFlush() throws MalformedURLException {
        URL url = new URL("file:/C:/Users/HP/IdeaProjects/login/out/production/login/Fxml/MainInterface.fxml");
        initialize(url,null);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
        //初始化个人头像
        Image image = new Image(LoginController.login_user.getPhoto());
        head.setImage(image);
        Circle cir = new Circle();
        cir.setCenterX(40.0);
        cir.setCenterY(40.0);
        cir.setRadius(40.0);
        DropShadow dropshadow = new DropShadow();// 阴影向外
        dropshadow.setRadius(10);// 颜色蔓延的距离
        dropshadow.setOffsetX(0);// 水平方向，0则向左右两侧，正则向右，负则向左
        dropshadow.setOffsetY(0);// 垂直方向，0则向上下两侧，正则向下，负则向上
        dropshadow.setSpread(0.1);// 颜色变淡的程度
        dropshadow.setColor(Color.BLACK);// 设置颜色
        cir.setEffect(dropshadow);
        head.setClip(cir);

        //初始化基本信息
        user_label.setText(LoginController.login_user.getLabel());
        user_name.setText(LoginController.login_user.getNickname());

        try {
            friendObsList = new user_getFriendList().getFriendList(LoginController.login_user.getAccount(),1);//从数据库获得用户的好友信息
            newFriendObsList = new user_getApply().getApply_msg(LoginController.login_user.getAccount(),1);//获取新的好友
            groupObsList = new user_getFriendList().getFriendList(LoginController.login_user.getAccount(),2);//获取群信息
            newGroupObsList = new user_getApply().getApply_msg(LoginController.login_user.getAccount(),2);//获取新的群聊
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        msgList.setCellFactory(new Callback<ListView, ListCell>(){
            @Override
            public ListCell<friend> call(ListView param){
                ListCell<friend> listcell = new ListCell<friend>() {
                    protected void updateItem(friend item, boolean empty){
                        super.updateItem(item, empty);
                        //生成好友列表的样式
                        if(!empty){
                            HBox box1 = new HBox(50);
                            box1.setPrefHeight(50.0);
                            Label l1 = new Label();
                            ImageView iv = new ImageView();
                            iv.setImage(new Image("/head_image/2.jpg"));
                            if(item.friend_head!=null){
                                iv.setImage(new Image(item.friend_head));

                            }
                            else{
                                iv.setImage(new Image("/head_image/2.jpg"));
                            }

                            iv.setFitHeight(40.0);
                            iv.setFitWidth(40.0);
                            Circle head = new Circle();
                            head.setCenterX(20.0);
                            head.setCenterY(20.0);
                            head.setRadius(20.0);

                            DropShadow dropshadow = new DropShadow();// 阴影向外
                            dropshadow.setRadius(3);// 颜色蔓延的距离
                            dropshadow.setOffsetX(0);// 水平方向，0则向左右两侧，正则向右，负则向左
                            dropshadow.setOffsetY(0);// 垂直方向，0则向上下两侧，正则向下，负则向上
                            dropshadow.setSpread(0.1);// 颜色变淡的程度
                            dropshadow.setColor(Color.BLACK);// 设置颜色
                            head.setEffect(dropshadow);
                            iv.setClip(head);
                            //在线状态,1为离线，0为在线
                            l1.setText(item.friend_remark);
                            box1.getChildren().addAll(iv,l1);
                            setGraphic(box1);
                            box1.setOnMouseClicked(new EventHandler() {
                                @Override
                                public void handle(Event event) {
                                    ChatController.friend_pitch = item;
                                    ChatController.user_pitch = LoginController.login_user;
                                    StageStart.ChatInterfaceStage chatInterfaceStage=new StageStart.ChatInterfaceStage();
                                    try {
                                        chatInterfaceStage.start(new Stage());
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
        msgList.setItems(friendObsList);

        //好友列表
        friendList.setCellFactory(new Callback<ListView, ListCell>(){
            @Override
            public ListCell<friend> call(ListView param){
                ListCell<friend> listcell = new ListCell<friend>() {
                    protected void updateItem(friend item, boolean empty) {
                        super.updateItem(item, empty);
                        //生成好友列表的样式
                        if(!empty){
                            HBox box1 = new HBox(50);
                            box1.setPrefHeight(50.0);
                            Label l1 = new Label();
                            ImageView iv = new ImageView();
                            if(item.friend_head!=null){
                                iv.setImage(new Image(item.friend_head));

                            }
                            else{
                                iv.setImage(new Image("/head_image/1.jpg"));
                            }
                            iv.setFitHeight(40.0);
                            iv.setFitWidth(40.0);
                            Circle head = new Circle();
                            head.setCenterX(20.0);
                            head.setCenterY(20.0);
                            head.setRadius(20.0);
                            iv.setClip(head);
                            l1.setText(item.friend_remark+" \n"+item.friend_label);

                            // 创建右键菜单
                            ContextMenu contextMenu = new ContextMenu();
                            // 菜单项
                            MenuItem redBg = new MenuItem("刷新");
                            redBg.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent event) {
                                    System.out.println("点击刷新");
                                    initialize(location, resources);
                                }
                            });
                            // 菜单项
                            MenuItem blueBg = new MenuItem("删除好友");
                            blueBg.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent event) {
                                    System.out.println("点击删除好友");

                                }
                            });
                            contextMenu.getItems().addAll(redBg, blueBg);
                            // 右键菜单===================================================================

                            // 添加右键菜单到label
                            l1.setContextMenu(contextMenu);
                            box1.getChildren().addAll(iv,l1);
                            setGraphic(box1);
                            box1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    if(event.getButton().name().equals("PRIMARY")){
                                        //点击好友头像得到好友
                                        FriendPageController.friendInformation = item;
                                        FriendPageController.mark = 1;
                                        StageStart.FriendPageStage friendPageStage=new StageStart.FriendPageStage();
                                        try {
                                            friendPageStage.start(new Stage());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
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
        friendList.setItems(friendObsList);

        //好友申请表
        newFriendList.setCellFactory(new Callback<ListView, ListCell>(){
            @Override
            public ListCell<Apply_msg> call(ListView param){
                ListCell<Apply_msg> listcell = new ListCell<Apply_msg>() {
                    protected void updateItem(Apply_msg item, boolean empty) {
                        super.updateItem(item, empty);
                        //生成new好友列表的样式
                        if(!empty) {
                            if (item.status == 1) {
                                HBox box1 = new HBox(50);
                                box1.setPrefHeight(50.0);
                                Label l1 = new Label();
                                ImageView head = new ImageView();
                                head.setFitHeight(40.0);
                                head.setFitWidth(40.0);
                                head.setImage(new Image("/head_image/1.jpg"));
                                User user = null;
                                try {
                                    System.out.println(item.sender_Account + " " + item.receiver_Account + " " + item.apply_msg + " " + item.status);
                                    user = getUserInformation(item.sender_Account);
                                } catch (SQLException | IOException e) {
                                    e.printStackTrace();
                                }
                                l1.setText(user.getNickname()+" \n"+item.apply_msg);
                                box1.getChildren().addAll(head, l1);
                                setGraphic(box1);

                                box1.setOnMouseClicked(new EventHandler() {
                                    @Override
                                    public void handle(Event event) {
                                        try {
                                            ApplyAddController.newFriend = user_getFriendList.getfrienddata(item.sender_Account);
                                            ApplyAddController.msg = item;
                                        } catch (SQLException | IOException e) {
                                            e.printStackTrace();
                                        }
                                        ApplyAddStage applyAddStage = new StageStart.ApplyAddStage();
                                        try {
                                            applyAddStage.start(new Stage());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });


                                head.setOnMouseClicked( new EventHandler() {
                                    @Override
                                    public void handle(Event event) {
                                        //点击好友头像得到好友
                                        friend C2 = null;
                                        try {
                                            C2 = user_getFriendList.getfrienddata(item.receiver_Account);
                                        } catch (SQLException | IOException e) {
                                            e.printStackTrace();
                                        }
                                        FriendPageController.friendInformation = C2;
                                        FriendPageController.mark = 0;
                                        FriendPageStage friendPageStage = new FriendPageStage();
                                        try {
                                            friendPageStage.start(new Stage());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                setText(null);
                                setGraphic(null);
                            }
                        }
                    }
                };
                return listcell;
            }
        });
        newFriendList.setItems(newFriendObsList);

        //群聊列表
        groupList.setCellFactory(new Callback<ListView, ListCell>(){
            @Override
            public ListCell<friend> call(ListView param){
                ListCell<friend> listcell = new ListCell<friend>() {
                    protected void updateItem(friend item, boolean empty) {
                        super.updateItem(item, empty);
                        //生成群聊列表的样式
                        if(!empty){
                           // System.out.println(item.friend_account+" "+item.myAccount);
                            HBox box1 = new HBox(50);
                            box1.setPrefHeight(50.0);
                            Label l1 = new Label();
                            ImageView iv = new ImageView(new Image("file:///D:/IdeaProjects/login/src/head_image/;群头像.png"));
                            iv.setFitHeight(40.0);
                            iv.setFitWidth(40.0);
                            Circle head = new Circle();
                            head.setCenterX(20.0);
                            head.setCenterY(20.0);
                            head.setRadius(20.0);
                            iv.setClip(head);
                            l1.setText(item.friend_remark);
                            box1.getChildren().addAll(iv,l1);
                            setGraphic(box1);
                            box1.setOnMouseClicked(new EventHandler() {
                                @Override
                                public void handle(Event event) {
                                    //点击群聊聊天界面  groupChat是点击
                                    GroupChatController.groupChat = item;
                                    try {
                                        //user_group是群聊界面用户本人
                                        GroupChatController.user_group = getUserInformation(item.friend_account);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    StageStart.GroupChatStage groupChatStage=new StageStart.GroupChatStage();
                                    try {
                                        groupChatStage.start(new Stage());
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
        groupList.setItems(groupObsList);

        //您有新的群聊
        newGroupList.setCellFactory(new Callback<ListView, ListCell>(){
            @Override
            public ListCell<Apply_msg> call(ListView param){
                ListCell<Apply_msg> listcell = new ListCell<Apply_msg>() {
                    protected void updateItem(Apply_msg item, boolean empty) {
                        super.updateItem(item, empty);
                        //生成new群聊列表的样式
                        if(!empty) {
                            if (item.status == 1) {
                                HBox box1 = new HBox(50);
                                box1.setPrefHeight(50.0);
                                Label l1 = new Label();
                                Button head = new Button();
                                head.setPrefHeight(40.0);
                                head.setPrefWidth(40.0);
                                User user = null;
                                try {
                                    System.out.println(item.sender_Account + " " + item.receiver_Account + " " + item.apply_msg + " " + item.status);
                                    user = getUserInformation(item.sender_Account);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                l1.setText(user.getNickname()+" \n"+item.apply_msg);
                                box1.getChildren().addAll(head, l1);
                                setGraphic(box1);

                                box1.setOnMouseClicked(new EventHandler() {
                                    @Override
                                    public void handle(Event event) {
                                        try {
                                            ApplyAddController.newFriend = user_getFriendList.getfrienddata(item.sender_Account);
                                            ApplyAddController.msg = item;
                                        } catch (SQLException | IOException e) {
                                            e.printStackTrace();
                                        }
                                        ApplyAddStage applyAddStage = new StageStart.ApplyAddStage();
                                        try {
                                            applyAddStage.start(new Stage());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });


                                head.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        //点击好友头像得到好友
                                        friend C2 = null;
                                        try {
                                            C2 = user_getFriendList.getfrienddata(item.receiver_Account);
                                        } catch (SQLException | IOException e) {
                                            e.printStackTrace();
                                        }
                                        FriendPageController.friendInformation = C2;
                                        FriendPageController.mark = 0;
                                        FriendPageStage friendPageStage = new FriendPageStage();
                                        try {
                                            friendPageStage.start(new Stage());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                setText(null);
                                setGraphic(null);
                            }
                        }
                    }
                };
                return listcell;
            }
        });
        newGroupList.setItems(newGroupObsList);

    }

    //由用户账号得到用户信息
    public static User getUserInformation(String account) throws SQLException, IOException {
        Statement stmt =MySqlDriver.GetStatement();
        ResultSet rs=stmt.executeQuery("select *from user");
        while(rs.next())
        {
            if(account.equals(rs.getString(1)))
            {
                User C1 = new User(account,rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
                return C1;
            }
        }
        return null;
    }

    @FXML
    public void clickMyHeadButton(MouseEvent event) throws Exception {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            StageStart.PersonalStage personalStage = new StageStart.PersonalStage();
            personalStage.start(new Stage());
        }
    }

    public void clickCreateGroup() throws IOException {
        StageStart.CreateGroupStage createGroupStage=new StageStart.CreateGroupStage();
        createGroupStage.start(new Stage());
    }

}
