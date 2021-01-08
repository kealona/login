package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class friend implements Serializable {
    public String friend_account;
    public String friend_head;
    public String friend_remark;
    public String friend_nickname;
    public String friend_sex;
    public String friend_birthday;
    public String friend_email;
    public String friend_online = "1";
    public String friend_label;
    public String myAccount;


    public friend(String friend_account,String myAccount,String friend_online,String friend_remark){
        this.friend_account = friend_account;
        this.friend_remark = friend_remark;
        this.myAccount = myAccount;
        this.friend_online = friend_online;//1为离线，0为在线
    }

    public friend(String friend_account,String friend_remark,String friend_sex,String friend_birthday,String friend_nickname){
        this.friend_account = friend_account;
        this.friend_remark = friend_remark;
        this.friend_birthday = friend_birthday;
        this.friend_sex = friend_sex;
        this.friend_nickname = friend_nickname;
    }

    public friend(String friend_account){
        this.friend_account = friend_account;
        friend addFriend = new friend();

    }

    public friend(){

    }

}
