package Controller;

import pojo.friend;
import Driver.MySqlDriver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FriendPageController implements Initializable {
    @FXML
    private Label faccount;
    @FXML
    private TextField fsex;
    @FXML
    private TextField fbirth;
    @FXML
    private TextField fphoneNum;
    @FXML
    private TextField femail;
    @FXML
    private TextField fnickname;
    @FXML
    private TextField fRemark;
    @FXML
    private ImageView fhead;
    @FXML
    private TextField friend_label;
    @FXML
    private Button addFriend;

    public static friend friendInformation;
    public static int mark = 0;//0为双方不是好友 1表示双方是好友


    /**
     * 初始化好友的信息
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(mark == 0){
            addFriend.setVisible(true);
        }
        else{
            addFriend.setVisible(false);
        }

        faccount.setText(friendInformation.friend_account);
        fphoneNum.setText(friendInformation.friend_email);
        fsex.setText(friendInformation.friend_sex);
        fnickname.setText(friendInformation.friend_nickname);
        fbirth.setText(friendInformation.friend_birthday);
        femail.setText(friendInformation.friend_email);
        friend_label.setText(friendInformation.friend_label);
        fRemark.setText(friendInformation.friend_remark);

        System.out.println("好友头像"+friendInformation.friend_head);
        if(friendInformation.friend_head==null){
            Image image = new Image("file:///D:/IdeaProjects/login/src/head_image/1.jpg");
            fhead.setImage(image);
        }
        else{
            Image image = new Image(friendInformation.friend_head);
            fhead.setImage(image);
        }

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
        fhead.setClip(cir);
    }

    /**
     * 点击编辑好友备注按钮
     */
    public void clickEditButton(){
        fRemark.setEditable(true);
    }

    /**
     * 点击提交好友的新备注
     * @throws SQLException 抛出sql语句异常
     */
    public void clickSubmit() throws SQLException {
        if(changeNodes(fRemark.getText())){
            System.out.println("修改好友备注");
        }
    }

    /**
     * 修改好友备注
     * @param notes 修改的新备注
     * @return 返回是否操作成功
     * @throws SQLException 抛出sql语句异常
     */
    public Boolean changeNodes(String notes) throws SQLException {
        PreparedStatement ChangeUser1=MySqlDriver.GetPreparedStatement("update friendlist set remark = ? where MyAccount= ? and friend_Account = ?");
        ChangeUser1.setString(1,notes);
        ChangeUser1.setString(2,LoginController.login_user.getAccount());
        ChangeUser1.setString(3,faccount.getText());
        ChangeUser1.executeUpdate();
        return true;
    }
}
