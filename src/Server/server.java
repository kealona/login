package Server;

import Dao.User;
import Dao.user_getFriendList;
import Controller.LoginController;
import pojo.Message;
import pojo.friend;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;

public class server {
    private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();

    public static void main(String... args) throws IOException, SQLException {
        System.out.println("------服务器------");
        //创建服务器
        //使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(9999);
        //阻塞式等待连接
        while(true){
            Socket client = server.accept();
            System.out.println("一个客户端建立了连接...");
            Channel c = new Channel(client);
            all.add(c);
            new Thread(c).start();
        }
    }
    static class Channel implements Runnable{
        private ObjectInputStream dis;
        private ObjectOutputStream dos;
        private boolean isRun;
        User user;

        public Channel(Socket client) throws SQLException {
            try {
                dis = new ObjectInputStream(client.getInputStream());
                dos = new ObjectOutputStream(client.getOutputStream());
                isRun = true;
                //欢迎到来
                //send("欢迎您的到来");
                // sendOthers(this.name + " 来到了聊天室", true);
                Message mesg = (Message) dis.readObject();
                this.user = mesg.sender;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Channel Wrong");
                release();
            }
        }

        //接收消息
        private Object receive() throws IOException, SQLException {
            Object msg = null;
            try {
                msg = dis.readObject();
                System.out.println("接收消息");
            } catch (ClassNotFoundException e) {
                System.out.println("Receive Wrong");
                release();
            }
            return msg;
        }

        private void sendOthers(Message msg,boolean systemMsg) throws SQLException {
            //找到接收者
            if (msg.format == 1 || msg.format == 2||msg.format == 4) {
                String targetName = msg.receiver.friend_account;
                try {
                    saveMsg.SaveMessage(msg);//保存信息
                } catch (SQLException | FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("发送给其他人");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Channel other : all) {
                    if (other.user.getAccount().equals(targetName)) {
                        other.send(msg);
                        return;
                    }
                }
            }
            else if(msg.format == 3||msg.format == 23||msg.format == 24){
                try {
                    int copy=msg.format;
                    if(msg.format==3||msg.format==23)
                    {
                        msg.format=3;
                    }
                    else msg.format=4;
                    saveMsg.SaveMessage(msg);
                    msg.format=copy;


                    ObservableList<friend> friendobslist = new user_getFriendList().getFriendList(msg.receiver.friend_account,1);
                    msg.group_send=msg.sender;
                    String C1=msg.group_send.getAccount();
                    msg.sender=new User(msg.receiver.friend_account);
                    for(int i=0;i<friendobslist.size();i++)
                    {
                        if(!(friendobslist.get(i).friend_account.equals(C1)))
                        {
                            msg.receiver=friendobslist.get(i);
                            saveMsg.SaveMessage(msg);
                        }

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (Channel other : all) {
                    if (other != this) {
                        other.send(msg);
                    }
                }
            }
        }

        private void send(Message msg) throws SQLException {
            try {
                dos.writeObject(msg);
                System.out.println(msg);
                dos.flush();
            } catch (IOException e) {
                System.out.println("send Wrong");
                release();
            }
        }

        //释放资源
        private void release() throws SQLException {
            this.isRun = false;

            ServerSxUtils.close(dis,dos);

            System.out.println("有人退出了聊天室");
            all.remove(this);

        }

        public void run(){
            while(isRun){
                Message msg = null;
                try {
                    msg = (Message) receive();
                } catch (IOException | SQLException e) {
                    System.out.println("run Wrong");
                    LoginController.login_user.setOnline("1");
                    try {
                        release();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                if (msg != null) {
                    try {
                        sendOthers(msg, false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        release();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
