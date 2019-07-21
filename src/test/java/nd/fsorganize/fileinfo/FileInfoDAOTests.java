package nd.fsorganize.fileinfo;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lombok.extern.slf4j.Slf4j;
import nd.fsorganize.fileinfo.FileInfo;
import nd.fsorganize.fileinfo.FileInfoDAO;

@Slf4j
//@RunWith(SpringRunner.class)
@RunWith(JUnit4.class)
//@SpringBootTest
public class FileInfoDAOTests {
	public static final String basedir = FileInfoServiceTest.basedir;
	public static final String testfil = basedir + "\\d.txt";
	
	@Test
	public void testgetCannonicalName() {
		final File fl = new File(testfil);
		final String ret = FileInfoDAO.getCannonicalName(fl);
		Assert.assertNotNull(ret);
		//Assert.assertEquals(testfil, ret);
		log.warn("Got name: " + ret);
	}
	@Test
	public void testgetFileInfo() {
        final File fl = new File(testfil);
	    final String cn = FileInfoDAO.getCannonicalName(fl);
		final FileInfo ret = FileInfoDAO.getFileInfo(fl);
		Assert.assertNotNull(ret);
		Assert.assertEquals(cn, ret.getName());
		Assert.assertEquals(FileInfo.Type.FILE, ret.getType());
		Assert.assertNotNull(ret.getCreateDate());
		Assert.assertNotEquals(0, ret.getCreateDate().getTime());
	}
}
