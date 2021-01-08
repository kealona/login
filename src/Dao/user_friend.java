package Dao;

import pojo.friend;

public class user_friend {
    public friend users_friend(User C1){
        friend C2 = new friend();
        C2.friend_account = C1.getAccount();
        C2.friend_birthday = C1.getBirthday();
        C2.friend_email = C1.getPhone_number();//手机号码和邮箱
        C2.friend_nickname = C1.getNickname();
        C2.friend_remark = C1.getNickname();//朋友的备注
        C2.friend_sex = C1.getSex();
        C2.friend_head = C1.getPhoto();


        return C2;
    }
}
