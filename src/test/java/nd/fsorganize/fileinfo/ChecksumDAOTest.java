package nd.fsorganize.fileinfo;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nd.fsorganize.fileinfo.ChecksumDAO;

@RunWith(JUnit4.class)
public class ChecksumDAOTest {
    private static Logger log = LoggerFactory.getLogger(ChecksumDAO.class);
    public static final String basedir = FileInfoServiceTest.basedir;
    public static final String testfil = FileAttribDAOTest.testfil;
    public static final String cksum = "ca55ce7f30305d6cc4c7a09cb7e066f2c4ec5dc0cecf311261047dffa854fe50";
    public static final byte[] cksby = {
                 (byte)0xca,(byte)0x55,(byte)0xce,(byte)0x7f,(byte)0x30,
                 (byte)0x30,(byte)0x5d,(byte)0x6c,(byte)0xc4,(byte)0xc7,
                 (byte)0xa0,(byte)0x9c,(byte)0xb7,(byte)0xe0,(byte)0x66,
                 (byte)0xf2,(byte)0xc4,(byte)0xec,(byte)0x5d,(byte)0xc0,
                 (byte)0xce,(byte)0xcf,(byte)0x31,(byte)0x12,(byte)0x61,
                 (byte)0x04,(byte)0x7d,(byte)0xff,(byte)0xa8,(byte)0x54,
                 (byte)0xfe,(byte)0x50
        };

    @Test
    public void checksumFileTest() {
        final File fl = new File(testfil);
        final File fsha = new File(testfil + ChecksumDAO.SHAEXT);
        fsha.delete();
        String cks = ChecksumDAO.checksumFile(fl, null);
        log.info("Checksum: {}", cks);
        Assert.assertNotNull(cks);
        Assert.assertNotEquals(0, cks.length());
        Assert.assertEquals(cksum, cks);
        // .sha3 file will be used now 
        cks = ChecksumDAO.checksumFile(fl, null);
        Assert.assertNotNull(cks);
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
        final byte[] cks = ChecksumDAO.checksumFileBytes(fl, null);
        Assert.assertNotNull(cks);
        Assert.assertNotEquals(0, cks.length);
        Assert.assertArrayEquals(cksby, cks);
    }
    /*
    @Test
    public void checksumFileBytesExitTest() throws Exception {
        final File fl = new File( FileAttribDAOTest.testfil);
        final byte[] cks = ChecksumDAO.checksumFileBytes(fl, new ChecksumDAO.StreamReader() {
            @Override
            public void readStream(InputStream ins) {
                Map<String, String> fileattr = null;
                try {
                    fileattr = FileAttribDAO.getAttribFile(ins);
                } catch (ImageProcessingException | IOException e) {
                    // TODO Auto-generated catch block
                    log.error("Exception: ", e);
                }
                Assert.assertNotNull(fileattr);
                Assert.assertNotEquals(0, fileattr.size());
            }
        });//994714 then 986522
        Assert.assertNotNull(cks);
        Assert.assertNotEquals(0, cks.length);
        //Assert.assertArrayEquals(cksby, cks);
    }
    */
}
