package pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apply_msg implements Serializable {
    //发送好友申请信息
    public String sender_Account;
    public String apply_msg;
    public String receiver_Account;
    public int status;


    /*public static Apply_msg getApply(String account) throws SQLException{
        Statement stmt =MySqlDriver.GetStatement();
        ResultSet rs=stmt.executeQuery("select *from apply_msg");
        friend C1 = new friend();
        while(rs.next())
        {
            if(account.equals(rs.getString(1)))
            {
                C1.friend_account=account;
                C1.friend_nickname=rs.getString(3);
                C1.friend_sex=rs.getString(4);
                C1.friend_birthday=rs.getString(5);
                break;
            }
        }
        return C1;
    }*/

}
