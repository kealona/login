package Controller;

import StageStart.AccountStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AccountController {
    @FXML
    private Button new_Account;

    /**
     * 用户的账号
     */
    public static String newAccount;

    /**
     * 得到账号
     */
    public void getAccount(){
        new_Account.setText(newAccount);
    }

    /**
     * 点击确认生成账号成功后，关闭当前窗口
     */
    public void clickConfirmButton(){
        AccountStage.ACCOUNT.close();
    }

}
