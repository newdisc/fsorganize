package nd.fsorganize.fileinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import lombok.extern.slf4j.Slf4j;
import nd.fsorganize.util.FSOrganizeException;

@Slf4j
public class ChecksumDAO {
    private static final String ALGO = "SHA3-256";
    private static final boolean WRITESUM = true;
    //c618cb648d104d5ee03b50138e5509e0f99da3901b4fbddccdf2c38e70bd125c
    //5a978688dc4a030191f07668542d3ad3ec9bafd82a6ef4eae2f71240516923ed2ee585bbd9644d191bc10e12afce8fd8d1ee9ca64ee215933570b423028977cd *IMG_20171022_115712.jpg
    public static interface StreamReader {
        public void readStream(InputStream str);
    }
    protected static byte[] checksumFileBytes(File input, StreamReader sr) 
            throws GeneralSecurityException, IOException {
        long start = System.currentTimeMillis();
        MessageDigest md = MessageDigest.getInstance(ALGO);
        byte[] ret;
        long counter = 0;
        try (final DigestInputStream dis = new DigestInputStream(new FileInputStream(input), md);) {
            if (null != sr) {
                sr.readStream(dis);
            }
            while (dis.read() != -1) counter++; //empty loop to clear the data
            log.info("Read bytes: " + counter);
            md = dis.getMessageDigest(); // Is this required - assignment
            ret = md.digest();
        }
        long end = System.currentTimeMillis();
        log.debug("Message Digest Time taken: " + (end-start) + " numbytes: " + counter);
        return ret;
    }
    protected static String checksumFile(File input, StreamReader sr) {
        try {
            final String shafname = input.getCanonicalPath() + ".sha3";
            final Path shapath = Paths.get(shafname);
            final File shafile = new File(shafname);
            String sret;
            if (shafile.exists() && shafile.isFile()) {
                sret = Files.readString(shapath);
                sret = sret.substring(0,64);
                log.debug("From File sha3-256 = " + sret);
            } else {
                byte[] ret = checksumFileBytes(input, sr);
                sret = bytesToHex(ret);
                log.debug("From Calculations sha3-256 = " + shafname + " sha: " + sret);
                //Files.isWritable(path)shapath.getParent().
                if (WRITESUM && Files.isWritable(shapath.getParent())) {
                    Files.writeString(shapath, sret);
                } else {
                    log.warn("Can/Will not write to shafile directory" + shafname);
                }
            }
            return sret;
        } catch (GeneralSecurityException | IOException e) {
            final String err = "Could not get digest of : " + input.getName();
            log.error(err);
            throw new FSOrganizeException(err, e);
        }
    }
    protected static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        log.debug("Checksum is: " + sb);
        return sb.toString();
    }
    private ChecksumDAO() {}
}
