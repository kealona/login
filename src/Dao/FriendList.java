package Dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendList {
    private String friend_Account;
    private String online;
    private String remark;
    private String myAccount;
}
