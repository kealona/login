package Server;

import pojo.Message;
import Driver.MySqlDriver;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class saveMsg {
    public static void SaveMessage(Message message) throws SQLException, IOException {
        System.out.println("保存消息");
        System.out.println("信息接收者:"+message.receiver.friend_account+" 信息发送者:"+message.sender.getAccount()
        +" 信息内容："+message.wordMsg+" 发送时间："+message.time);
        PreparedStatement  C1= MySqlDriver.GetPreparedStatement("insert into message values(?,?,?,?,?,?,?,?)");
        if(message.format == 1||message.format == 3) {
            C1.setString(1, message.sender.getAccount());
            C1.setString(2, message.receiver.friend_account);
            C1.setString(3, message.wordMsg);
            C1.setString(4, message.time);
            C1.setInt(5,message.format);
            C1.setString(6,null);
            C1.setString(7,null);
            if(message.group_send != null)
            {
                C1.setString(8,message.group_send.getAccount());
            }
            else
                C1.setString(8,null);
            C1.executeUpdate();
        }
        else if(message.format == 2||message.format == 4||message.format == 23||message.format == 24) {
            System.out.println("file:"+message.image+" fileData:"+message.fileData.getName());
            C1.setString(1, message.sender.getAccount());
            C1.setString(2, message.receiver.friend_account);
            C1.setString(3, message.wordMsg);
            C1.setString(4, message.time);
            C1.setInt(5, message.format);
            File file = new File(message.image);
            InputStream in = null;
            in = new FileInputStream(file);
            C1.setBinaryStream(6,in,(int)file.length());
            C1.setString(7,message.fileData.getName());
            if(message.group_send != null)
            {
                C1.setString(8,message.group_send.getAccount());
            }
            else
                C1.setString(8,null);
            C1.executeUpdate();
            in.close();
        }

    }
}
