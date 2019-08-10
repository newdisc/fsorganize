package nd.fsorganize.fileinfo;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lombok.extern.slf4j.Slf4j;
import nd.fsorganize.fileinfo.FileTraverseDAO;

@Slf4j
@RunWith(JUnit4.class)
public class FileTraverseDAOTest {
    public static final String basedir = FileInfoDAOTests.basedir;
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
