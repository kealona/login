package Server;

import Dao.User;
import pojo.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Send implements Runnable{
    ObjectOutputStream dos;
    public boolean isRun = true;
    public Message mes_flag;
    public Socket client;

    //初始化
    public Send(Socket client, User name) {
        try {
            dos = new ObjectOutputStream(client.getOutputStream());
            this.client = client;
            Message masg = new Message(0, name);
            send(masg);
        } catch (IOException e) {
            System.out.println("发送信息的线程");
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(isRun){
            send(mes_flag);
        }
    }
    public void send(Message msg_send){
        try {
            if(msg_send!=null)
            {
                dos.writeObject(msg_send);
                dos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("发送消息1");
            release();
        }
    }

        private void release(){
            this.isRun = false;
            try {
                SxUtils.close(dos, client);
            } catch (Exception e) {
                System.out.println("发送消息释放资源");
                release();
            }
        }
}
