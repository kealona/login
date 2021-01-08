package Controller;

import Driver.MySqlDriver;
import StageStart.ApplyStage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ApplyController implements Initializable {

    public static String ffaccount;
    public static String ffnickname;

    /**
     * 构造一条好友申请信息
     * @param account 好友的账号
     * @param nickname 好友的用户名
     * @param sex 好友的性别
     */
    public static void Apply(String account, String nickname, String sex){
        ffaccount = account;
        ffnickname = nickname;
    }

    @FXML
    private Label addFriendAccount;
    @FXML
    private Label addFriendNickname;
    @FXML
    private TextArea validate_msg;


    /**
     * 初始化申请好友信息
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addFriendAccount.setText(ffaccount);
        addFriendNickname.setText(ffnickname);

    }

    /**
     * 点击发送好友申请信息
     * @throws SQLException 抛出sql语句异常
     */
    public void clickApply() throws SQLException {
        if(!checkFriend(LoginController.login_user.getAccount(),ffaccount)){
            //点击发送好友申请
            System.out.println("双方不是好友");
            if(applyMsg(LoginController.login_user.getAccount(),ffaccount,validate_msg.getText(),1)) {
                ApplyStage.ADD.close();
                System.out.println("发送好友申请");
            }

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"ERROR: 已经和对方是好友！");
            alert.titleProperty().set("添加失败");
            alert.headerTextProperty().set("添加失败");
            alert.showAndWait();
        }

    }

    /**
     * 在数据库查询双方是否是好友
     * @param loginAccount 用户的账号
     * @param fAccount 好友的账号
     * @return 返回查询结果，查询双方是好友返回FALSE，反之返回TRUE
     * @throws SQLException 抛出sql语句异常
     */
    public static Boolean checkFriend(String loginAccount,String fAccount) throws SQLException {

        PreparedStatement stmt = MySqlDriver.GetPreparedStatement("select *from friendlist where myAccount=? and friend_Account=?");
        stmt.setString(1,loginAccount);
        stmt.setString(2,fAccount);
        ResultSet rs=stmt.executeQuery();
        if(rs.next()){
            System.out.println("双方已经是好友");
            return true;
        }
        return false;
    }

    /**
     * 向数据库中添加一条好友申请信息
     * @param user_account 用户账号
     * @param newFriendAccount 发出申请的接收者账号
     * @param apply_msg 申请信息
     * @param status 申请状态
     * @return 返回操作结果
     * @throws SQLException 抛出sql语句异常
     */
    public static Boolean applyMsg(String user_account,String newFriendAccount,String apply_msg,int status) throws SQLException {
        PreparedStatement  C1= MySqlDriver.GetPreparedStatement("insert into apply_msg values(?,?,?,?)");
        C1.setString(1,user_account);
        C1.setString(2,newFriendAccount);
        C1.setString(3,apply_msg);
        C1.setInt(4,status);//1为申请中，2为同意，3为拒绝
        C1.executeUpdate();
        return true;
    }

}
