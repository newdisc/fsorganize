package nd.fsorganize.fileinfo;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lombok.extern.slf4j.Slf4j;
import nd.fsorganize.fileinfo.ChecksumDAO;

@Slf4j
@RunWith(JUnit4.class)
public class ChecksumDAOTest {
	public static final String basedir = FileInfoDAOTests.basedir;
	public static final String testfil = FileInfoDAOTests.testfil;
	public static final String cksum = "045b2b0478f563d09f53fc160bdb1bb112da425376d9fa8e6ef312f7ae518b50";
	public static final byte[] cksby = {
	        (byte)0x04,(byte)0x5b,(byte)0x2b,(byte)0x04,(byte)0x78,(byte)0xf5,
	        (byte)0x63,(byte)0xd0,(byte)0x9f,(byte)0x53,(byte)0xfc,(byte)0x16,
	        (byte)0x0b,(byte)0xdb,(byte)0x1b,(byte)0xb1,(byte)0x12,(byte)0xda,
	        (byte)0x42,(byte)0x53,(byte)0x76,(byte)0xd9,(byte)0xfa,(byte)0x8e,
	        (byte)0x6e,(byte)0xf3,(byte)0x12,(byte)0xf7,(byte)0xae,(byte)0x51,
	        (byte)0x8b,(byte)0x50
		};

	@Test
	public void checksumFileTest() {
		final File fl = new File(testfil);
		final String cks = ChecksumDAO.checksumFile(fl);
		log.debug("Checksum: " + cks);
		Assert.assertNotNull(cks);
		Assert.assertNotEquals(0, cks.length());
		Assert.assertEquals(cksum, cks);
	}
	@Test
	public void bytesToHexTest() {
		final String cks = ChecksumDAO.bytesToHex(cksby);
		Assert.assertNotNull(cks);
		Assert.assertNotEquals(0, cks.length());
		Assert.assertEquals(cksum, cks);
	}
	@Test
	public void checksumFileBytesTest() throws Exception {
		final File fl = new File(testfil);
		final byte[] cks = ChecksumDAO.checksumFileBytes(fl);
		Assert.assertNotNull(cks);
		Assert.assertNotEquals(0, cks.length);
		Assert.assertArrayEquals(cksby, cks);
	}
}
