package Controller;

import Dao.user_getFriendList;
import pojo.friend;
import Driver.MySqlDriver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddFriendController {
    @FXML
    private TextField searchNum;

    /**
     * 点击岔村按钮
     * @throws Exception 抛出全部异常
     */
    public void clickSearch() throws Exception {
        String account;
        account = searchNum.getText();
        //查找该账号是否存在
        if(CheckAccount(account)){
            System.out.println("该账号存在");
            friend friend = new friend();
            friend = user_getFriendList.getfrienddata(account);
            ApplyController.Apply(friend.friend_account,friend.friend_nickname,friend.friend_sex);
            StageStart.ApplyStage applyStage=new StageStart.ApplyStage();
            applyStage.start(new Stage());
        }
        else{
            System.out.println("该账号不存在");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.titleProperty().set("ERROR");
            alert.headerTextProperty().set("该账号不存在");
            alert.showAndWait();
        }


    }

    /**
     * 在数据库检查账号是否存在
     * @param account 需要查询的账号
     * @return 返回查询的结果
     * @throws SQLException 抛出sql语句异常
     * @throws IOException 抛出IO异常
     */
    public static Boolean CheckAccount(String account) throws SQLException, IOException {
        PreparedStatement stmt = MySqlDriver.GetPreparedStatement("select *from user where account=?");
        stmt.setString(1,account);
        ResultSet rs=stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }


}
