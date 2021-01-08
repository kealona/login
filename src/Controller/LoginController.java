package Controller;

import Dao.User;
import Driver.MySqlDriver;
import Server.client;
import StageStart.LoginStage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import StageStart.MainInterfaceStage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField userAccount;
    @FXML
    private TextField userPassword;
    @FXML
    private ImageView headImage;

    public client client_login;
    public static User login_user;

    /**
     * 点击注册按钮
     * @throws IOException 抛出IO异常
     */
    public void clickRegisterButton() throws IOException {
        StageStart.RegisterStage registerStage=new StageStart.RegisterStage();
        registerStage.start(new Stage());
    }

    /**
     * 点击忘记密码按钮
     * @throws Exception
     */
    public void clickForgetButton() throws Exception {
        StageStart.ForgetStage ForgetStage=new StageStart.ForgetStage();
        ForgetStage.start(new Stage());
    }

    /**
     * 点击关闭窗口的按钮
     */
    public void clickCloseBtn(){
        LoginStage.LOGIN.close();
    }

    /**
     * 点击最小化窗口
     */
    public void clickMiniBtn(){
        LoginStage.LOGIN.setIconified(true);
    }

    /**
     * 点击登录按钮
     * 在数据库查找数据是否正确
     * @throws Exception
     */
    public void clickLoginButton() throws Exception {

        if(!userLogin()){
            System.out.print("登陆失败");

        }
        else {
            System.out.println("登陆成功");
            client_login = new client(login_user.getAccount());
            client_login.run();
            if(UserOnline(login_user,1)){//flag为1表示在线，flag为2表示离线
                LoginStage.LOGIN.close();
                MainInterfaceStage mainInterfaceStage = new StageStart.MainInterfaceStage();
                mainInterfaceStage.start(new Stage());
            }

        }
    }

    /**
     * 用户登录
     * @return 返回查询数据库的结果
     * @throws IOException
     */
    public boolean userLogin() throws IOException{
        User user=null;
        if (userAccount.getText().equals("") || userPassword.getText().equals("")) {
            System.out.println("输入为空！");
            return false;
        }
        //验证数据库里面有没有
        try {
            user=selectUser(userAccount.getText(),userPassword.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user==null){
            System.out.println("数据库中没有该数据");
            return false;
        }
       else{
            System.out.println("数据库中有该数据");
            return true;
        }

    }

    /**
     * 在数据库里查询数据是否正确
     * @param account 用户账号
     * @param password 用户密码
     * @return 返回查询成功的用户信息
     * @throws SQLException 抛出sql语句异常
     * @throws IOException 抛出IO流异常
     */
    //在数据库中查询账号密码是否正确
    public User selectUser(String account,String password) throws SQLException,IOException {
        User user =null;
        PreparedStatement stmt = MySqlDriver.GetPreparedStatement("select *from user where account=? and password=?");
        stmt.setString(1,account);
        stmt.setString(2,password);
        ResultSet rs=stmt.executeQuery();
        if(rs.next()){
            user=new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), (com.mysql.jdbc.Blob) rs.getBlob(7),rs.getString(8),rs.getString(10));
            login_user = user;
            return user;
        }
        return null;
    }

    /**
     * 检查用户在线的状态
     * @param newUser 需要检查的用户
     * @param flag 查询的标记，如果是1就标记这个用户登录，如果是2就标记这个用户登出
     * @return 返回用户是否在线
     * @throws SQLException 抛出sql语句异常
     */
    //用户在线
    public static boolean UserOnline(User newUser,int flag) throws SQLException {
        //数据库存储
        PreparedStatement ChangeUser1=MySqlDriver.GetPreparedStatement("update user set Online = ? where account= ?");
        if(flag == 1) {
            ChangeUser1.setString(1, "0");
            ChangeUser1.setString(2, newUser.getAccount());
            ChangeUser1.executeUpdate();
            return true;
        }
        else{
            ChangeUser1.setString(1, "1");
            ChangeUser1.setString(2, newUser.getAccount());
            ChangeUser1.executeUpdate();
            System.out.println("退出");
            return true;
        }
    }


    /**
     * 初始化方法
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Circle cir = new Circle();
        cir.setCenterX(37.0);
        cir.setCenterY(36.0);
        cir.setRadius(36.0);
        DropShadow dropshadow = new DropShadow();// 阴影向外
        dropshadow.setRadius(3);// 颜色蔓延的距离
        dropshadow.setOffsetX(0);// 水平方向，0则向左右两侧，正则向右，负则向左
        dropshadow.setOffsetY(0);// 垂直方向，0则向上下两侧，正则向下，负则向上
        dropshadow.setSpread(0.1);// 颜色变淡的程度
        dropshadow.setColor(Color.BLACK);// 设置颜色
        cir.setEffect(dropshadow);
        headImage.setClip(cir);

    }
}
