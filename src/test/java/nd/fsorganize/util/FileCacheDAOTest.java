package nd.fsorganize.util;

import org.junit.Assert;
import org.junit.Before;
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
    private FileCacheDAO<FileInfoTreeNode> fcd;
    
    @Before
    public void setDAO() {
        fcd = new FileCacheDAO<FileInfoTreeNode>(
                new TypeReference<FileInfoTreeNode>() {});
    }
    private FileInfoTreeNode readCacheTest() {
        final String fname  = rootDir + "resTestCache.fidb";
        final FileInfoTreeNode fitn = fcd.readCache(fname);
        return fitn;
    }
    @Test
    public void readCachePubTest() {
        final FileInfoTreeNode fitn = readCacheTest();
        Assert.assertNotNull(fitn);
        log.warn("FileTreeNode: " + JSONFileDAO.objectToJsonS(fitn));
    }
    @Test
    public void writeCacheTest() {
        final FileInfoTreeNode fitn = readCacheTest();
        log.warn("Testing WriteCache");
        final String fname  = rootDir + "testCacheCreate.fidb";
        fcd.writeCache(fname, fitn);
        Assert.assertTrue(true);
        //writeCache(final String fname, Object cache)
    }
}
