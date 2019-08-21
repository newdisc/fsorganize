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

import nd.fsorganize.fileinfo.FileAttribDAOTest;
import nd.fsorganize.fileinfo.FileInfo;
import nd.fsorganize.fileinfo.FileInfoServiceTest;
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
        log.debug("Read data: " + jfd);
    }
   @Test(expected = FSOrganizeException.class)
   public void readResourceTestExcept()  throws JsonProcessingException,  IOException {
       final String resName = "NonExistingFile.txt";
       final JSONFileDAO<FileInfo> jfd = new JSONFileDAO<FileInfo>();
       jfd.readResource(resName, new TypeReference<List<FileInfo>>() {});
   }
   @Test
   public void getResourceFileNameTest() {
       final String fname = JSONFileDAO.getResourceFileName(FileAttribDAOTest.jpgres);
       final String expname = FileInfoServiceTest.testResources + FileAttribDAOTest.jpgres;
       //  /C:/dev/wksp/fsorganize/target/test-classes/nd/fsorganize/fileinfo/PANO_20171001_074215.jpg
       Assert.assertEquals(expname, fname);
   }
   
   @Test
   public void objectStringTest() {
       Assert.assertTrue(true);
   }
}
