package nd.fsorganize.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import nd.fsorganize.fileinfo.FileInfoServiceTest;
import nd.fsorganize.fileinfo.FileInfoTreeNode;

@RunWith(JUnit4.class)
public class FileCacheDAOTest {
    private static Logger log = LoggerFactory.getLogger(JSONFileDAOTest.class);
    private static final String rootDir = FileInfoServiceTest.testResources1;
    @Test
    public void readCacheTest() {
        final FileCacheDAO<FileInfoTreeNode> fcd = new FileCacheDAO<>();
        final String fname  = rootDir + "testCache.fidb";
        final TypeReference<FileInfoTreeNode> tr = 
                new TypeReference<FileInfoTreeNode>() {};
        final FileInfoTreeNode fitn = fcd.readCache(fname, tr);
        Assert.assertNotNull(fitn);
        log.warn("FileTreeNode: " + JSONFileDAO.objectToJson(fitn));
    }
    @Test
    public void writeCacheTest() {
        final TypeReference<FileInfoTreeNode> tr = 
                new TypeReference<FileInfoTreeNode>() {};
        final FileCacheDAO<FileInfoTreeNode> fcd = new FileCacheDAO<>();
        final String fnameread  = rootDir + "testCache.fidb";
        final FileInfoTreeNode fitn = fcd.readCache(fnameread, tr);
        log.warn("Testing WriteCache");
        final String fname  = rootDir + "testCacheCreate.fidb";
        fcd.writeCache(fname, fitn);
        Assert.assertTrue(true);
        //writeCache(final String fname, Object cache)
    }
}
