package Controller;

import Dao.User;
import Driver.MySqlDriver;
import StageStart.RegisterStage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class RegisterController implements Initializable {

    @FXML
    private RadioButton registersexMan;
    @FXML
    private RadioButton registersexWoman;
    @FXML
    private TextField nickname;
    @FXML
    private TextField register_password;
    @FXML
    private TextField register_phoneNum;
    @FXML
    private DatePicker user_birthday;
    @FXML
    private TextField code;


    public static String newAccount;
    public String getCode;

    /**
     * 点击注册按钮
     * @throws Exception 抛出异常
     */
    public void FinishRegisterButton() throws Exception {
        if(!isValid()){
            System.out.println("注册信息不合理！");
        }
        else if(!getCode.equals(code.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.titleProperty().set("验证码不正确");
            alert.headerTextProperty().set("注册失败");
            alert.showAndWait();
        }
        else {
            //打开生成账号的窗口
            System.out.println("注册成功");
            RegisterStage.REGISTER.close();
            AccountController.newAccount = createAccount();
            StageStart.AccountStage AccountStage=new StageStart.AccountStage();
            AccountStage.start(new Stage());
        }
    }

    /**
     * 点击获取验证码按钮，生成邮箱验证码
     */
    public void clickSendCode(){
        String code = "";
        Random random = new Random();
        for(int i=0;i<6;i++){
            int r = random.nextInt(10);
            code = code + r;
        }
        getCode = code;
        HtmlEmail send = new HtmlEmail();//创建一个HtmlEmail实例对象
        // 获取随机验证码
        String resultCode = code;
        try {
            send.setHostName("smtp.qq.com");
            send.setAuthentication("2274748854@qq.com", "hfmzdpghxlltdhja"); //第一个参数是发送者的QQEamil邮箱   第二个参数是刚刚获取的授权码

            send.setFrom("2274748854@qq.com", "QQ聊天室");//发送人的邮箱为自己的，用户名可以随便填  记得是自己的邮箱不是qq
            send.setSSLOnConnect(true); //开启SSL加密
            send.setCharset("utf-8");
            send.addTo(register_phoneNum.getText());  //设置收件人    email为你要发送给谁的邮箱账户   上方参数
            send.setSubject("QQ短信验证码"); //邮箱标题
            send.setMsg(resultCode); //Email发送的内容
            send.send();  //发送
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建一个账号
     * @return 返回生成的账号
     */
    public static String createAccount(){
        String str1 ="0123456789";
        Random random = new Random();
        StringBuffer str2 = new StringBuffer();
        for(int i=0;i<10;i++){
            int number = random.nextInt(str1.length());
            char charAt = str1.charAt(number);
            str2.append(charAt);
        }
        System.out.println("生成账号："+str2.toString());
        return str2.toString();
    }

    /**
     * 单选性别
     * @return 返回选择的结果
     */
    public String selectSex() {
        if (registersexMan.isSelected()) {
            return "男";
        } else
            return "女";
    }

    /**
     * 检查注册信息是否合理
     * @return 如果合理返回true，有误返回false
     * @throws SQLException 抛出sql语句异常
     */
    public boolean isValid() throws SQLException{
        String password = register_password.getText();
        if(password.length()>7&&password.length()<=15){
            for(int i=0;i<password.length();i++){
                String str = password.substring(i,i+1);
                if(str.equals(" ")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.titleProperty().set("密码不能有空格！");
                    alert.headerTextProperty().set("注册失败");
                    alert.showAndWait();
                    System.out.println("有空字符串");
                    return false;
                }
            }
            //密码中是否有字母和数字
            for(int i=0;i<password.length();i++){
                char word = password.charAt(i);
                if(!Character.isLetterOrDigit(word)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.titleProperty().set("密码必须含有字母和数字");
                    alert.headerTextProperty().set("注册失败");
                    alert.showAndWait();
                    System.out.println("密码不正确");
                    return false;
                }
            }
            newAccount = createAccount();
            System.out.println("密码正确");
            User newUser = new User(newAccount, register_password.getText(), nickname.getText(), selectSex(), register_phoneNum.getText(),user_birthday.getValue().toString(),"12");
            System.out.println("注册成功 " + saveUser(newUser));
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.titleProperty().set("密码长度应在7-16位");
        alert.headerTextProperty().set("注册失败");
        alert.showAndWait();
        System.out.println("密码过短");
        return false;
    }

    /**
     * 添加用户信息
     * @param newUser 用户的信息
     * @return 返回操作是否成功
     * @throws SQLException 抛出sql语句异常
     */
    public boolean saveUser(User newUser) throws SQLException {
        //数据库存储
        PreparedStatement  C1= MySqlDriver.GetPreparedStatement("insert into user values(?,?,?,?,?,?,?,?,?,?)");
        C1.setString(1,newUser.getAccount());
        C1.setString(2,newUser.getPassword());
        C1.setString(3,newUser.getNickname());
        C1.setString(4,newUser.getSex());
        C1.setString(5,newUser.getPhone_number());
        if(user_birthday.getValue().toString()==""){
            C1.setString(6,null);
        }
        else{
            C1.setString(6, user_birthday.getValue().toString());
        }
        C1.setString(7,null);
        C1.setString(8,null);
        C1.setString(9,"1");
        C1.setString(10,null);
        C1.executeUpdate();
        return true;
    }

    /**
     * 选择性别男
     */
    public void man(){
        if(registersexWoman.isSelected())
        {
            registersexWoman.setSelected(false);
        }
        registersexMan.setSelected(true);
    }

    /**
     * 选择性别女
     */
    public void lady(){
        if(registersexMan.isSelected())
        {
            registersexMan.setSelected(false);
        }
        registersexWoman.setSelected(true);
    }

    /**
     * 生成验证码
     * @return 返回验证码
     */
    private char randomChar(){
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return s.charAt(r.nextInt(s.length()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nickname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(nickname.getLength()!=0){
                    nickname.setStyle("-fx-background-color: #d0e0e3");
                    if(nickname.getLength()<=10){
                        nickname.setStyle("-fx-border-color: #53d153;-fx-border-radius: 2px;");
                    }
                    else{
                        nickname.setStyle("-fx-border-color: red;-fx-border-radius: 2px");
                    }
                }
            }
        });
        register_password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(register_password.getLength()!=0){
                    register_password.setStyle("-fx-background-color: #d0e0e3");
                }
            }
        });
    }
}
