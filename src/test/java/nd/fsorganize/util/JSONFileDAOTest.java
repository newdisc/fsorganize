package nd.fsorganize.util;

import java.io.IOException;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import nd.fsorganize.fileinfo.FileInfo;
import nd.fsorganize.util.JSONFileDAO;

@RunWith(JUnit4.class)
public class JSONFileDAOTest {
    private static Logger log = LoggerFactory.getLogger(JSONFileDAOTest.class);
   @Test
    public void readResourceTest() throws JsonProcessingException,  IOException {
        final String resName = "docsscan.txt";
        //printClasspath();
        final JSONFileDAO<FileInfo> jfd = new JSONFileDAO<FileInfo>();
        final List<FileInfo> finfs = jfd.readResource(resName, new TypeReference<List<FileInfo>>() {});
        Assert.assertNotNull(finfs);
        Assert.assertNotEquals(0, finfs.size());
        log.warn("Read data: " + jfd);
    }
   @Test(expected = FSOrganizeException.class)
   public void readResourceTestExcept()  throws JsonProcessingException,  IOException {
       final String resName = "NonExistingFile.txt";
       final JSONFileDAO<FileInfo> jfd = new JSONFileDAO<FileInfo>();
       jfd.readResource(resName, new TypeReference<List<FileInfo>>() {});
   }
}
