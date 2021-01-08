package pojo;

import Driver.MySqlDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class user_getApply {
    public ObservableList<Apply_msg> getApply_msg(String account,int flag) throws SQLException, IOException {

        ObservableList<Apply_msg> applyObsList= FXCollections.observableArrayList();
        Statement stmt = MySqlDriver.GetStatement();
        ResultSet rs=stmt.executeQuery("select *from apply_msg");

        if(flag == 1) {
            while (rs.next()) {
                if (account.equals(rs.getString(2))) {
                    //获得好友验证消息  status为1申请中，为2同意，为3拒绝
                    if (rs.getInt(4) == 1) {
                        System.out.println("获取申请好友的消息");
                        Apply_msg C1 = new Apply_msg(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                        System.out.println("申请人：" + rs.getString(1) + " 验证消息：" + rs.getString(3)
                                + " 发送给：" + rs.getString(2) + " 状态：" + rs.getString(4));

                        applyObsList.add(C1);
                    }
                }
            }
        }
       /* else if(flag == 2){
            while (rs.next()) {
                if (account.equals(rs.getString(2))) {
                    //获得群聊验证消息  status为1申请中，为2同意，为3拒绝
                    if (rs.getInt(4) == 1) {
                        System.out.println("获取申请好友的消息");
                        Apply_msg C1 = new Apply_msg(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                        System.out.println("申请人：" + rs.getString(1) + " 验证消息：" + rs.getString(3)
                                + " 发送给：" + rs.getString(2) + " 状态：" + rs.getString(4));

                        applyObsList.add(C1);
                    }
                }
            }
        }*/
        return applyObsList;
    }
}
