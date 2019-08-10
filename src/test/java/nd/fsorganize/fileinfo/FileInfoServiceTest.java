package nd.fsorganize.fileinfo;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lombok.extern.slf4j.Slf4j;
import nd.fsorganize.fileinfo.FileInfoService;

@Slf4j
@RunWith(JUnit4.class)
public class FileInfoServiceTest {
    public static final String basedir = "src/test/resources";
    private FileInfoService fileSvc;
    
    @Before
    public void setSvc() {
        fileSvc = new FileInfoService();
    }
    @Test
    public void testDirList() {
        long start = System.currentTimeMillis();
        final String root = basedir;
        FileInfoTreeNode finfs = fileSvc.getFiles(root);
        long end = System.currentTimeMillis();
        log.warn("Entire File List Time taken: " + (end-start));
        Assert.assertNotNull(finfs);
        Assert.assertNotEquals(0, finfs.getChildren().size());
    }
}
 