package Controller;

import Dao.User;
import Driver.MySqlDriver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PersonalController implements Initializable {
    @FXML
    private TextField MyAccount;
    @FXML
    private TextField MyBirth;
    @FXML
    private TextField MyNickName;
    @FXML
    private TextField MyAge;
    @FXML
    private TextField MyEmail;
    @FXML
    private TextField MyPhoneNum;
    @FXML
    private ImageView MyHead;
    @FXML
    private TextField MyLabel;
    @FXML
    private ComboBox selectSex;

    public String Sex;


    public void clickEditButton(){
        MyBirth.setEditable(true);
        MyEmail.setEditable(true);
        MyNickName.setEditable(true);
        MyPhoneNum.setEditable(true);
        MyLabel.setEditable(true);
    }

    /**
     * 点击修改用户的头像
     * @param event 鼠标点击事件
     * @throws SQLException 抛出sql语句异常
     * @throws IOException 抛出IO异常
     */
    @FXML
    public void Change_head(MouseEvent event) throws SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择需要的打开的头像");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Photo Files"
                , "*.jpg", "*.png", "*.bmp", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(new Stage());
        System.out.println("图片地址："+file.getAbsolutePath());
        if (file != null) {
            change_myHead(file, LoginController.login_user.getAccount());//修改数据库里的数据
            userdata_flush(file.getAbsolutePath());
        }
    }

    /**
     * 用户头像刷新
     * @param path 用户头像的地址
     * @throws IOException 抛出IO异常
     */
    public void userdata_flush(String path) throws IOException//刷新
    {
        Image im = new Image("file:///" + path);
        MyHead.setImage(im);
    }

    /**
     * 在数据库修改用户的头像
     * @param C1 用户的头像信息
     * @param account 用户账号
     * @throws SQLException 抛出sql语句异常
     */
    public void change_myHead(File C1,String account) throws SQLException {
        PreparedStatement NewUser= MySqlDriver.GetPreparedStatement("update user set photo= ? where account=?");
        NewUser.setString(2,account);
        InputStream input=null;
        try {
            input=new FileInputStream(C1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        NewUser.setBinaryStream(1, input, (int)C1.length());
        NewUser.executeUpdate();

    }

    /**
     * 点击保存个人信息按钮
     * @throws SQLException 抛出sql语句异常
     * @throws IOException 抛出IO异常
     */
    public void clickSubmit() throws SQLException, IOException {
        User userData = new User(MyAccount.getText(),MyNickName.getText(),Sex,MyPhoneNum.getText(),MyBirth.getText(),MyAge.getText());
        if(queryUser(userData)){
            System.out.println("数据更新，修改个人信息");
        }
        MyBirth.setEditable(false);
        MyEmail.setEditable(false);
        MyNickName.setEditable(false);
        MyPhoneNum.setEditable(false);
        MyLabel.setEditable(false);
    }

    /**
     * 把用户修改的数据保存到数据库
     * @param newUser 用户的全部信息
     * @return 返回操作是否成功
     * @throws SQLException 抛出sql异常
     */
    public boolean queryUser(User newUser) throws SQLException {
        PreparedStatement ChangeUser1=MySqlDriver.GetPreparedStatement("update user set nickname = ? , sex = ? , phone_number = ? , birthday = ? , label = ? where account= ?");
        ChangeUser1.setString(1,newUser.getNickname());
        ChangeUser1.setString(2,Sex);
        ChangeUser1.setString(3,newUser.getPhone_number());
        ChangeUser1.setString(4,newUser.getBirthday());
        ChangeUser1.setString(5,newUser.getLabel());
        ChangeUser1.setString(6,newUser.getAccount());
        ChangeUser1.executeUpdate();
        return true;
    }

    /**
     * 初始化用户的所有信息
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MyAccount.setText(LoginController.login_user.getAccount());
        MyPhoneNum.setText(LoginController.login_user.getPhone_number());
        selectSex.setPromptText(LoginController.login_user.getSex());
        MyNickName.setText(LoginController.login_user.getNickname());
        MyBirth.setText(LoginController.login_user.getBirthday());
        MyLabel.setText(LoginController.login_user.getLabel());

        Image image = new Image(LoginController.login_user.getPhoto());
        MyHead.setImage(image);
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
        MyHead.setClip(cir);


        selectSex.setItems(FXCollections.observableArrayList("男","女"));
        selectSex.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Sex = newValue.toString();
            }
        });
    }
}
