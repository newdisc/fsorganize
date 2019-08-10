package nd.fsorganize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nd.fsorganize.fileinfo.FileInfoService;

@SpringBootApplication
public class FsorganizeApplication {
    FileInfoService fileSvc = new FileInfoService();
    @Bean
    FileInfoService getFileInfoService() {
        return fileSvc;
    }
    public static void main(String[] args) {
        SpringApplication.run(FsorganizeApplication.class, args);
    }
}
