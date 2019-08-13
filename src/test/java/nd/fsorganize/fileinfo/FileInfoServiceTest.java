package nd.fsorganize.fileinfo;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nd.fsorganize.fileinfo.FileInfoService;
import nd.fsorganize.util.FSOrganizeException;

@RunWith(JUnit4.class)
public class FileInfoServiceTest {
    private static Logger log = LoggerFactory.getLogger(FileInfoServiceTest.class);
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
        log.warn("Entire File List Time taken: {}", (end-start));
        Assert.assertNotNull(finfs);
        Assert.assertNotEquals(0, finfs.getChildren().size());
    }
    @Test
    public void testDirListException() {
        final String root = "NONExistingDirectory";
        boolean caught = false;
        try {
            fileSvc.getFiles(root);
        } catch (FSOrganizeException e) {
            caught = true;
        }
        Assert.assertTrue(caught);
    }
}
 