package nd.fsorganize.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;
import nd.fsorganize.fileinfo.FileInfo;
import nd.fsorganize.util.JSONFileDAO;

@Slf4j
@RunWith(JUnit4.class)
public class JSONFileDAOTest {
    public void printClasspath() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            log.warn(url.getFile());
        }        
    }
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
}
