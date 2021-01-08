package Server;

import pojo.Message;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import static Controller.ChatController.CHATWINDOW;
import static Controller.GroupChatController.GroupChatWindow;

public class Receive implements Runnable {
    ObjectInputStream dis;
    public Socket client;
    public boolean isRun;

    public Receive(Socket client){
        this.client = client;
        isRun = true;
        try {
            dis = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("接收消息的线程");
            release();
        }
    }

    public void run(){
        while(isRun){
            Message msg = receive();
            if(msg!=null){
                Platform.runLater(()->{
                    if(msg.format != 3 && msg.format!=23 && msg.format!=24) {
                        CHATWINDOW.msreceive(msg);
                    }
                    else{
                        GroupChatWindow.gMsgReceive(msg);
                    }
                });
            }
        }
    }

    public Message receive(){
        Message msg =null;
        try{
            msg = (Message)dis.readObject();
            String C1 = (msg.wordMsg);
            msg.wordMsg = C1;
        } catch (IOException e){
            System.out.println("receive");
            release();
        } catch (ClassNotFoundException e){
            System.out.println("receive");
            release();
        }
        return msg;
    }

    private void release(){
        this.isRun = false;
        try {
            SxUtils.close(dis, client);
        } catch (Exception e) {
            System.out.println("接收消息释放资源");
            release();
        }
    }
}
