package Controller;

import Driver.MySqlDriver;
import StageStart.AccountStage;
import StageStart.CreateGroupStage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateGroupController {
    @FXML
    private TextField Group_name;

    /**
     * 点击提交创建一个群聊
     * @throws Exception
     */
    public void clickSubmitCreate() throws Exception {
        String Account = AccountController.newAccount = RegisterController.createAccount();
        addGroup(Account,LoginController.login_user.getAccount());
        CreateGroupStage.Create.close();
        StageStart.AccountStage AccountStage=new StageStart.AccountStage();
        AccountStage.start(new Stage());
    }

    /**
     * 创建群聊，保存入数据库
     * @param GAccount 群聊的账号
     * @param myAccount 群主的账号
     * @throws SQLException 抛出sql语句异常
     */
    public void addGroup(String GAccount,String myAccount) throws SQLException {
        PreparedStatement C1= MySqlDriver.GetPreparedStatement("insert into friendlist values(?,?,?,?)");
        C1.setString(1,GAccount);
        C1.setString(2,myAccount);
        C1.setString(3,"3");
        C1.setString(4,Group_name.getText());
        C1.executeUpdate();
    }
}
