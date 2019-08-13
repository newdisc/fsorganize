package nd.fsorganize.fileinfo;

import java.io.IOException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class FileAttribDAO {
    private static Logger log = LoggerFactory.getLogger(FileAttribDAO.class);
    public static Map<String, String> getAttribFile(final InputStream fis) throws ImageProcessingException, IOException {
        final Map<String, String> ret = new HashMap<>();
        log.debug("Retrieving file Exif/Meta Attributes:" );
        Metadata metadata = ImageMetadataReader.readMetadata(fis);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                log.debug(tag.toString() + " : Name : {} : Description : {}", tag.getTagName(), tag.getDescription());
                final String name = tag.getTagName();
                final String value = tag.getDescription();
                ret.put(name, value);
            }
        }
        return ret;
    }
    private FileAttribDAO() {}
}
