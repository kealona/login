package Controller;

import pojo.Apply_msg;
import pojo.friend;
import Driver.MySqlDriver;
import StageStart.ApplyAddStage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ApplyAddController implements Initializable {

    @FXML
    private TextField addFriendRemark;
    @FXML
    private Label addFriendNickname;
    @FXML
    private Label addFriendMsg;

    public static friend newFriend = null;
    public static Apply_msg msg = null;

    /**
     * 点击同意好友申请按钮
     * @throws SQLException 抛出sql语句异常
     */
    public void clickAccept() throws SQLException {
        //同意加好友 status状态为2 数据库添加friendList
        if(changeStatus(newFriend.friend_account,LoginController.login_user.getAccount(),1)&&addFriendList(LoginController.login_user.getAccount(),newFriend.friend_account)) {
            ApplyAddStage.ADD.close();
        }
    }

    /**
     * 点击拒绝好友申请按钮
     * @throws SQLException 抛出sql语句异常
     */
    public void clickRefused() throws SQLException {
        if(changeStatus(newFriend.friend_account,LoginController.login_user.getAccount(),2)&&addFriendList(LoginController.login_user.getAccount(),newFriend.friend_account)){
            ApplyAddStage.ADD.close();
        }
    }


    /**
     * 初始化好友申请消息
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //验证消息
        addFriendMsg.setText(msg.apply_msg);
    }

    /**
     * 对数据库改变好友申请的状态
     * @param user 用户自己的账号
     * @param friend 用于同意的好友账号
     * @param flag 申请状态 1为同意 2为拒绝
     * @return 返回对数据库操作是否成功
     * @throws SQLException 抛出sql语句异常
     */
    public Boolean changeStatus(String user,String friend,int flag) throws SQLException {
        PreparedStatement ChangeUser1= MySqlDriver.GetPreparedStatement("update apply_msg set status = ? where sender_Account= ? and receiver_Account= ?");
        //1为同意 2为拒绝
        if(flag ==1) {
            ChangeUser1.setInt(1, 2);
        }
        else{
            ChangeUser1.setInt(1, 3);
        }
        ChangeUser1.setString(2,user);
        ChangeUser1.setString(3,friend);//param代表第几个问号

        ChangeUser1.executeUpdate();
        return true;
    }

    /**
     * 向数据库添加一条好友申请
     * @param user 用户自己的账号
     * @param friend 用户想添加的好友账号
     * @return 返回数据库操作是否添加成功
     * @throws SQLException 抛出sql语句异常
     */
    //向数据库中添加好友
    public Boolean addFriendList(String user,String friend) throws SQLException {
        PreparedStatement  C1= MySqlDriver.GetPreparedStatement("insert into friendlist values(?,?,?,?)");
        C1.setString(1,user);
        C1.setString(2,friend);
        C1.setString(3,"1");
        C1.setString(4,addFriendRemark.getText());
        C1.executeUpdate();

        PreparedStatement  C2= MySqlDriver.GetPreparedStatement("insert into friendlist values(?,?,?,?)");
        C2.setString(1,friend);
        C2.setString(2,user);
        C2.setString(3,"1");
        C2.setString(4,addFriendNickname.getText());
        C2.executeUpdate();

        return true;
    }
}
