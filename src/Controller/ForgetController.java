package Controller;

import Driver.MySqlDriver;
import StageStart.ForgetStage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForgetController {
    @FXML
    private TextField newpassword;
    @FXML
    private TextField confirmpassword;
    @FXML
    private TextField forgetAccount;
    @FXML
    private TextField forgetNickname;
    @FXML
    private TextField forgetPhoneNum;

    /**
     * 点击重置密码按钮
     * @throws SQLException 抛出sql语句异常
     */
    public void clickResetbutton() throws SQLException {
        if(confirm_Password()&&savePassword(forgetAccount.getText(),forgetNickname.getText(),forgetPhoneNum.getText())){
            System.out.println("重置密码成功");
            ForgetStage.FORGET.close();
        }
        else
        {
            System.out.println("ERROR");
        }
    }

    /**
     * 确认两次密码是否一直
     * @return 如果相同返回true
     */
    public boolean confirm_Password(){
        if(newpassword.getText().equals(confirmpassword.getText())){
            return true;
        }
        System.out.println("两次密码不一致");
        return false;
    }

    /**
     * 保存新密码
     * @param Account 用户账号
     * @param Nickname 用户的昵称
     * @param PhoneNum 用户的电话号码
     * @return 返回用户是否操作成功
     * @throws SQLException 抛出sql语句异常
     */
    public boolean savePassword(String Account,String Nickname,String PhoneNum) throws SQLException {
        //数据库存储
        PreparedStatement ChangeUser1=MySqlDriver.GetPreparedStatement("update user set password = ? where account= ? and nickname = ? and phone_number = ?");
        ChangeUser1.setString(1,newpassword.getText());
        ChangeUser1.setString(2,Account);
        ChangeUser1.setString(3,Nickname);
        ChangeUser1.setString(4,PhoneNum);
        ChangeUser1.executeUpdate();
        return true;
    }
}
