package nd.fsorganize.fileinfo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public class ThumbNailDAOTest {
    private static Logger log = LoggerFactory.getLogger(FileInfoDAOTests.class);
    public static final String basedir = FileInfoServiceTest.basedir;
    public static final String testfil = FileAttribDAOTest.testfil;

    @Test
    public void getThumbNailTest() {
        final byte[] thmb = ThumbNailDAO.getThumbNail(testfil);
        Assert.assertNotNull(thmb);
        log.warn("Retrieved ThumbNail of : {}", testfil );
    }
}
