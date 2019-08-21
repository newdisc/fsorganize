package nd.fsorganize.fileinfo;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageProcessingException;

@RunWith(JUnit4.class)
public class FileAttribDAOTest {
    private static Logger log = LoggerFactory.getLogger(FileAttribDAOTest.class);
    public static final String basedir = FileInfoServiceTest.basedir;
    public static final String jpgres = "/nd/fsorganize/fileinfo/PANO_20171001_074215.jpg";
    public static final String testfil = basedir + jpgres;

    @Test
    public void getAttribFileTest() throws IOException, ImageProcessingException {
        log.info("Get File Attributes test: ");
        final InputStream ins = new FileInputStream(testfil);
        final Map<String, String> fileattr = FileAttribDAO.getAttribFile(ins);
        Assert.assertNotNull(fileattr);
        Assert.assertNotEquals(0, fileattr.size());
        ins.close();
    }
}
