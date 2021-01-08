package pojo;

import java.io.*;

public class ChangeToImage {
    public  String toImageData(byte[] data,String image_name){
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        String filePath = ("C:/Users/HP/IdeaProjects/login/src/Fxml/CSS/image" + image_name);
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            fos = new FileOutputStream(dir);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
