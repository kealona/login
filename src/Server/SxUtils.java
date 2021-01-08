package Server;

import java.io.Closeable;
import java.io.IOException;

public class SxUtils {
    public static void close(Closeable... targets) {
        for (Closeable target : targets) {
            try {
                if (null != target)
                    target.close();
            } catch (IOException e) {
                System.out.println("释放资源2");
            }
        }
    }
}
