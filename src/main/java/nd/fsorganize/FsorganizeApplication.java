package nd.fsorganize;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nd.fsorganize.fileinfo.FileInfoService;

@SpringBootApplication
public class FsorganizeApplication {
    private static Logger log = LoggerFactory.getLogger(FsorganizeApplication.class);
    FileInfoService fileSvc = new FileInfoService();
    @Bean
    FileInfoService getFileInfoService() {
        return fileSvc;
    }
    public static void main(String[] args) {
        if (0==args.length) {
            SpringApplication.run(FsorganizeApplication.class, args);
        } else {
            log.error("Command line arguments should NOT be present");
        }
    }
}
