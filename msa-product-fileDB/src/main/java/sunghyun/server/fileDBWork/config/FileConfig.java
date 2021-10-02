package sunghyun.server.fileDBWork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PreDestroy;
import java.io.*;

@Configuration
public class FileConfig {
    private final String path = "src/main/resources/product";

    @Bean
    public File file() throws IOException {
        productInitMkdir(path);
        return new File(path);
    }

    /*
    @Bean
    public RandomAccessFile randomAccessFile(File file) throws IOException {
        return new RandomAccessFile(file + "/0", "rw");
    }
     */

    public void productInitMkdir(String pPath) {
        File folder = new File(pPath);
        if (!folder.exists()) {
            try {
                folder.mkdir();
                folder = new File(path);
                System.out.println("새로운 폴더를 생성합니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("이미 폴더가 있습니다.");
        }
    }
}
