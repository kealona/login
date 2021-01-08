package Dao;

import com.mysql.jdbc.Blob;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.sql.SQLException;

@Data
@NoArgsConstructor
public class User implements Serializable{
    private String account;
    private String password;
    private String nickname;
    private String sex;
    private String phone_number;
    private String birthday;
    private String age;
    private String photo;
    private String Online;
    private String label;

    public User(String account, String password, String nickname, String sex, String phone_number, String birthday, Blob photo, String age, String label) throws SQLException, IOException {
        this.account = account;
        this.password = password;
        this.nickname = nickname;
        this.sex = sex;
        this.phone_number = phone_number;
        this.birthday = birthday;
        this.age = age;
        this.label = label;

        if(photo!=null){
            InputStream in_image = new BufferedInputStream(photo.getBinaryStream());
            OutputStream out_image = new DataOutputStream(new FileOutputStream("src/head_image/new_head.jpg"));
            byte my_data[] = new byte[1024];
            int len = -1;
            while((len = in_image.read(my_data))!=-1){
                out_image.write(my_data,0,len);
            }
            in_image.close();
            out_image.close();
        }
        this.photo = "file:///D:/IdeaProjects/login/src/head_image/1.jpg";
    }

    public User(String account, String nickname, String sex, String phone_number, String birthday, String age) {
        this.account = account;
        this.nickname = nickname;
        this.sex = sex;
        this.phone_number = phone_number;
        this.birthday = birthday;
        this.age = age;

    }

    public User(String account, String password,String nickname, String sex, String phone_number, String birthday, String age) {
        this.account = account;
        this.nickname = nickname;
        this.sex = sex;
        this.phone_number = phone_number;
        this.birthday = birthday;
        this.age = age;
        this.password = password;

    }

    public User(String account){
        this.account = account;
    }

    public String getAccount() {
        return account;
    }
}
