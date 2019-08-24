package nd.fsorganize.fileinfo;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nd.fsorganize.util.FSOrganizeException;

public class ChecksumDAO {
    private static Logger log = LoggerFactory.getLogger(ChecksumDAO.class);
    private static final String ALGO = "SHA3-256";
    //c618cb648d104d5ee03b50138e5509e0f99da3901b4fbddccdf2c38e70bd125c
    //5a978688dc4a030191f07668542d3ad3ec9bafd82a6ef4eae2f71240516923ed2ee585bbd9644d191bc10e12afce8fd8d1ee9ca64ee215933570b423028977cd *IMG_20171022_115712.jpg
    @java.lang.SuppressWarnings("squid:S4790") // Safe to use hashing - we use only to check duplicates and verify contents
    protected static byte[] checksumFileBytes(File input) 
            throws GeneralSecurityException, IOException {
        long start = System.currentTimeMillis();
        MessageDigest md = MessageDigest.getInstance(ALGO);
        byte[] ret;
        long counter = 0;
        try (final DigestInputStream dis = new DigestInputStream(new FileInputStream(input), md);) {
            while (dis.read() != -1) counter++; //empty loop to clear the data
            log.debug("Read bytes: {}", counter);
            md = dis.getMessageDigest(); // Is this required - assignment
            ret = md.digest();
        }
        long end = System.currentTimeMillis();
        log.debug("Message Digest Time taken: {} numbytes: {}", (end-start), counter);
        return ret;
    }
    protected static String checksumFile(File input) {
        try {
            String sret;
            byte[] ret = checksumFileBytes(input);
            sret = bytesToHex(ret);
            log.debug("From Calculations sha3-256 = {}", sret);
            //Files.isWritable(path)shapath.getParent().
            return sret;
        } catch (GeneralSecurityException | IOException e) {
            throw FSOrganizeException.raiseAndLog("Could not get digest of : " + input.getName(), e, log);
        }
    }
    protected static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        log.debug("Checksum is: {}", sb);
        return sb.toString();
    }
    private ChecksumDAO() {}
}
