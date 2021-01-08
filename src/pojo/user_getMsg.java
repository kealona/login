package pojo;

import Dao.user_getFriendList;
import Controller.MainInterfaceController;
import Driver.MySqlDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class user_getMsg {
    public ObservableList<Message> getMsgList(String account,String FAccount) throws SQLException, IOException {
        ObservableList<Message> obslist= FXCollections.observableArrayList();
        Statement stmt = MySqlDriver.GetStatement();
        ResultSet rs=stmt.executeQuery("select *from message");

        while(rs.next())
        {
            if(account.equals(rs.getString(1))&&FAccount.equals(rs.getString(2)))
            {
                if(rs.getInt(5)==1) {
                    Message C1 = new Message(1, MainInterfaceController.getUserInformation(rs.getString(1)),new user_getFriendList().getfrienddata(rs.getString(2)) ,
                            rs.getString(3),rs.getString(4));
                    obslist.add(C1);
                }
                else if(rs.getInt(5)==2){
                    Message C1 = new Message(2, MainInterfaceController.getUserInformation(rs.getString(1)),new user_getFriendList().getfrienddata(rs.getString(2)) ,
                            rs.getString(3),rs.getString(4));
                    obslist.add(C1);
                }
            }
            else if(account.equals(rs.getString(2))&&FAccount.equals(rs.getString(1))){
                if(rs.getInt(5)==1) {
                    Message C1 = new Message(1, MainInterfaceController.getUserInformation(rs.getString(1)),new user_getFriendList().getfrienddata(rs.getString(2)) ,
                            rs.getString(3),rs.getString(4));
                    obslist.add(C1);
                }
                else if(rs.getInt(5)==2){
                    Message C1 = new Message(2, MainInterfaceController.getUserInformation(rs.getString(1)),new user_getFriendList().getfrienddata(rs.getString(2)) ,
                            rs.getString(3),rs.getString(4));
                    obslist.add(C1);
                }
            }
        }
        return obslist;
    }
}
