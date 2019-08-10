package nd.fsorganize.fileinfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.drew.imaging.ImageProcessingException;

import lombok.extern.slf4j.Slf4j;
import nd.fsorganize.fileinfo.FileInfo.Type;
import nd.fsorganize.util.FSOrganizeException;

@Slf4j
@Component
public class FileInfoDAO {
    private static class ImageAttributeReader implements ChecksumDAO.StreamReader {
        private Map<String, String> readAttributes = null;
        public Map<String, String> getReadAttributes() {
            return readAttributes;
        }
        @Override
        public void readStream(InputStream str) {
            try {
                readAttributes = FileAttribDAO.getAttribFile(str);
            } catch (ImageProcessingException | IOException e) {
                final String err = "Could NOT get ImageAttributes from stream" + e.getMessage();
                log.error(err, e);
                throw new FSOrganizeException(err, e);
            }
        }
    }
    public static FileInfo getFileInfo(final File fl) {
        long start = System.currentTimeMillis();
        final String fname = getCannonicalName(fl);
        final BasicFileAttributes bfn;
        try {
            bfn = Files.readAttributes(fl.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            final String err = "Could not find basic attributes of : " + fl.getName();
            log.error(err);
            throw new FSOrganizeException(err, e);
        }
        
        final FileInfo.Type type;
        if (bfn.isDirectory()) {
            type = Type.DIRECTORY;
        } else if (bfn.isRegularFile()) {
            type = Type.FILE;
        } else {
            type = Type.OTHER;
        }
        
        final FileInfo finf = new FileInfo();
        finf.setName(fname);
        finf.setCreateDate(new Date(bfn.creationTime().toMillis()));
        finf.setType(type);
        finf.setBytes(bfn.size());
        final ImageAttributeReader imgattr = new ImageAttributeReader();
        if (type == Type.FILE) {
            final String checksum = ChecksumDAO.checksumFile(fl, imgattr);
            finf.setChecksum(checksum);
        }
        long end = System.currentTimeMillis();
        finf.setProctime(end-start);
        log.debug("FileInfo Found: " + finf);
        log.info("Image Attributes: " + imgattr.getReadAttributes());
        return finf;
    }
    public static String getCannonicalName(final File fl) {
        final String fname;
        try {
            fname = fl.getCanonicalPath();
        } catch (IOException e) {
            final String err = "Could not find canonical path of : " + fl.getName();
            log.error(err);
            throw new FSOrganizeException(err, e);
        }
        return fname;
    }
    private FileInfoDAO() {}
}
