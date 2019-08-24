package nd.fsorganize.fileinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import nd.fsorganize.util.FSOrganizeException;

public class FileAttribDAO {
    private static Logger log = LoggerFactory.getLogger(FileAttribDAO.class);
    private static final String GPSDFZ = "yyyy:MM:dd hh:mm:ss.SSS z";
    private static final String IMGDF = "yyyy:MM:dd hh:mm:ss";
    private static final String GPSDT = "GPS Date Stamp";

    public static Map<String, String> getAttribFile(final File fi) {
        try {
            return FileAttribDAO.getAttribFile(new FileInputStream(fi));
        } catch (ImageProcessingException | IOException e) {
            throw FSOrganizeException.raiseAndLog(
                    "Could not get Image Attributes of : " + fi.getName(), e, log);
        }
    }
    public static Map<String, String> getAttribFile(final InputStream fis) throws ImageProcessingException, IOException {
        final Map<String, String> ret = new HashMap<>();
        log.debug("Retrieving file Exif/Meta Attributes:" );
        Metadata metadata = ImageMetadataReader.readMetadata(fis);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                log.debug("{} : Name : {} : Description : {}", tag, tag.getTagName(), tag.getDescription());
                final String name = tag.getTagName();
                final String value = tag.getDescription();
                ret.put(name, value);
            }
        }
        return ret;
    }
    public static void updateImageInfo(final File fl, final FileInfo finf) {
        final Map<String, String> imgattr = FileAttribDAO.getAttribFile(fl);
        log.debug("Image Attributes: {}", imgattr);
        setDateTime(finf, imgattr);
        setLocation(finf, imgattr);
        log.debug("GPS Date: {} {}", imgattr.get(GPSDT), imgattr.get("GPS Time-Stamp"));
    }
    public static void setDateTime(final FileInfo finf, final Map<String, String> imgattr) {
        log.debug("File Date: {}", finf.getCreateDate());
        if (imgattr.containsKey("Date/Time Digitized")) {
            final String imgattrdt = imgattr.get("Date/Time Digitized");
            final DateFormat imgOrgDF = new SimpleDateFormat(IMGDF);
            try {
                final Date gpsdt = imgOrgDF.parse(imgattrdt);
                finf.setCreateDate(gpsdt);
                log.debug("ImageAttr File Date Set to: {}", finf.getCreateDate());
            } catch (ParseException e) {
                log.error("Ignoring GPS parse Exception: {}", imgattrdt);
            }
        }
        if (imgattr.containsKey(GPSDT)) {
            final DateFormat gpsDF = new SimpleDateFormat(GPSDFZ);
            final String gpsstr = imgattr.get(GPSDT) + " " + imgattr.get("GPS Time-Stamp"); 
            try {
                final Date gpsdt = gpsDF.parse(gpsstr);
                finf.setCreateDate(gpsdt);
                log.debug("File Date Set to: {}", finf.getCreateDate());
            } catch (ParseException e) {
                log.error("Ignoring GPS parse Exception: {}", gpsstr);
            }
            
        }
    }
    public static void setLocation(final FileInfo finf, final Map<String, String> imgattr) {
        if (imgattr.containsKey("GPS Latitude")) {
            final String dmslat  = imgattr.get("GPS Latitude");
            final String ddlat = toDDLocation(dmslat);
            final String dmslong  = imgattr.get("GPS Longitude");
            final String ddlong = toDDLocation(dmslong);
            finf.setLocation(ddlat + "," + ddlong);
        }
    }
    public static String toDDLocation(final String dmsLocation) {
        final int deg = Integer.parseInt(dmsLocation.substring(0,2));
        final int min = Integer.parseInt(dmsLocation.substring(4,6));
        float sec = Float.parseFloat(dmsLocation.substring(8,12));
        sec += min * 60;
        sec = sec / 3600;
        final int fsec = (int)(sec * 1000000.0);
        return String.format("%s.%d", deg, fsec);
    }
    private FileAttribDAO() {}
}
