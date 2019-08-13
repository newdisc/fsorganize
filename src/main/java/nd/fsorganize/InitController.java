package nd.fsorganize;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nd.fsorganize.fileinfo.FileInfoService;
import nd.fsorganize.fileinfo.FileInfoTreeNode;

@RestController
public class InitController {
    private static Logger log = LoggerFactory.getLogger(InitController.class);

    @Autowired
    FileInfoService fileSvc;
    
    //Only Dev add CrossOrigin so that web and java run independently
    //@CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/init")
    public FileInfoTreeNode getFiles( @RequestParam final String directory) {
        log.info("Initializing File List: {}", directory);
        final FileInfoTreeNode ret = fileSvc.getFiles(directory);
        log.debug("Finished Initializing File List: {}", directory);
        return ret;
    }
}
