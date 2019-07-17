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
	public static final String cksum = "c618cb648d104d5ee03b50138e5509e0f99da3901b4fbddccdf2c38e70bd125c";
	public static final byte[] cksby = {
			(byte)0xc6,(byte)0x18,(byte)0xcb,(byte)0x64,(byte)0x8d,(byte)0x10,
			(byte)0x4d,(byte)0x5e,(byte)0xe0,(byte)0x3b,(byte)0x50,(byte)0x13,
			(byte)0x8e,(byte)0x55,(byte)0x09,(byte)0xe0,(byte)0xf9,(byte)0x9d,
			(byte)0xa3,(byte)0x90,(byte)0x1b,(byte)0x4f,(byte)0xbd,(byte)0xdc,
			(byte)0xcd,(byte)0xf2,(byte)0xc3,(byte)0x8e,(byte)0x70,(byte)0xbd,
			(byte)0x12,(byte)0x5c
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
