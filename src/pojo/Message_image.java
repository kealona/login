package pojo;

import java.io.Serializable;

public class Message_image implements Serializable {
    private String image_name;  //图片名
    private byte[] image_data;  //图片内容

    public Message_image(String name, byte[] content) {
        this.image_name = name;
        this.image_data = content;
    }
    public String getName() {
        return image_name;
    }

    public byte[] getContent() {
        return image_data;
    }
}
