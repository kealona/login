package Server;

import Controller.LoginController;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.Socket;

public class client {
    private static String account;
    private static Send send;
    private static Receive receive;

    public client(String userID){
        this.account = userID;
    }

    public void run(){
        System.out.println("-----客户端-----");
        try {
            //创建套接字，指定服务器地址和端口，连接服务器
            Socket client = new Socket("localhost",9999);

            //发送信息线程
            send = new Send(client, LoginController.login_user);
            new Thread(send).start();

            //接收信息线程
            //接收信息
            Receive receive = new Receive(client);
            new Thread(receive).start();
            LoginController.login_user.setOnline("0");

        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.titleProperty().set("AlwaysOnline");
            alert.headerTextProperty().set("服务器连接失败！");
            alert.showAndWait();
        }
    }
    public static Send getSend(){
        return send;
    }


}
