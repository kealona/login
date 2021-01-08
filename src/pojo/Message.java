package pojo;


import Dao.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    public int format = 1;
    public String time;
    public User sender;
    public friend receiver;
    public String wordMsg;//文字消息
    public String image;//图片消息
    public Message_image fileData;
    public User group_send;

    //文字消息
    public Message(int format,User sender,friend receiver,String wordMsg,String time){
        this.time = time;
        this.wordMsg = wordMsg;
        this.receiver = receiver;
        this.sender = sender;
        this.format = format;

    }

    //系统消息
    public Message(int format,User sender){
        this.format = format;
        this.sender = sender;
    }

    //图片消息
    public Message(int format,User sender,friend receiver,String main_msg,String time,String filename,byte[] imageData){
        this.time = time;
        this.sender = sender;
        this.format = format;
        this.receiver = receiver;
        this.image = main_msg;
        this.wordMsg = "file:///" + main_msg;//图片地址
        this.fileData = new Message_image(filename,imageData);
    }


}
