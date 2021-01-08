package Dao;

import pojo.friend;
import Driver.MySqlDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class user_getFriendList {
    public ObservableList<friend> getFriendList(String account, int flag) throws SQLException, IOException {
        ObservableList<friend> friendobslist= FXCollections.observableArrayList();
        Statement stmt = MySqlDriver.GetStatement();
        ResultSet rs=stmt.executeQuery("select *from friendlist");

        while(rs.next())
        {
            //获取好友备注
            if(flag==1&&rs.getString(3).equals("1"))
            {
                if(account.equals(rs.getString(2)))
                {
                    friend C1 = getfrienddata(rs.getString(1));
                    C1.friend_remark=rs.getString(4);
                    friendobslist.add(C1);
                }
            }
            //获取群列表
            else if(flag==2)
            {
                if(account.equals(rs.getString(2))&&rs.getString(3).equals("3"))
                {
                    friend C1 = new friend(rs.getString(1),account,rs.getString(3),rs.getString(4));
                    friendobslist.add(C1);
                }
            }
            //获取群成员
            else if(flag == 3){
                if(account.equals(rs.getString(1))&&rs.getString(3).equals("3"))
                {
                    friend C1 = getfrienddata(rs.getString(2));
                    friendobslist.add(C1);
                }
            }

        }
        return friendobslist;
    }

    public static friend getfrienddata(String account) throws SQLException, IOException {
        Statement stmt =MySqlDriver.GetStatement();
        ResultSet rs=stmt.executeQuery("select *from user");
        friend C1 = new friend();
        while(rs.next())
        {
            if(account.equals(rs.getString(1)))
            {

                C1.friend_account=account;
                C1.friend_nickname=rs.getString(3);
                C1.friend_sex=rs.getString(4);
                C1.friend_birthday=rs.getString(5);
                C1.friend_label = rs.getString(10);
                //C1.friend_online =

                if(rs.getBlob(7)!=null){
                    InputStream rs_image=new BufferedInputStream(rs.getBlob(7).getBinaryStream());
                    OutputStream os_image=new DataOutputStream(new FileOutputStream("src/" +
                            "head_image/"+ C1.friend_account+".jpg"));
                    byte datas[]=new byte[1024];
                    int len=-1;
                    while((len=rs_image.read(datas))!=-1)
                    {
                        os_image.write(datas,0,len);
                    }
                    rs_image.close();
                    os_image.close();
                }
                new JFXPanel();
                C1.friend_head="file:///D:/IdeaProjects/login/src/head_image/"+C1.friend_account+".jpg";


                break;
            }
        }
        return C1;
    }

}
