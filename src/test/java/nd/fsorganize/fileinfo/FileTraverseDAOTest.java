package nd.fsorganize.fileinfo;


import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nd.fsorganize.fileinfo.FileTraverseDAO;

@RunWith(JUnit4.class)
public class FileTraverseDAOTest {
    private static Logger log = LoggerFactory.getLogger(FileTraverseDAOTest.class);
    public static final String basedir = FileInfoServiceTest.basedir;
    @Test
    public void getFilesTest() {
        log.info("Testing Traversal");
        long start = System.currentTimeMillis();
        final File fl = new File(basedir);
        final Stream<File> files = FileTraverseDAO.getFiles(fl);
        List <File> flst = files.collect(Collectors.toList());
        Assert.assertNotNull(flst);
        Assert.assertNotEquals(0, flst.size());
        long end = System.currentTimeMillis();
        log.warn("Found " + flst.size() + " elements under: " + basedir + " in " + (end - start) + " milliseconds");
    }

}
