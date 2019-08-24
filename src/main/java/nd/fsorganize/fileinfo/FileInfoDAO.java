package nd.fsorganize.fileinfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import nd.fsorganize.fileinfo.FileInfo.Type;
import nd.fsorganize.util.FSOrganizeException;

@Component
public class FileInfoDAO {
    private static Logger log = LoggerFactory.getLogger(FileInfoDAO.class);
    
    public static FileInfo getFileInfo(final File fl, final String rootDir1) {
        final Path rootPath = Paths.get(rootDir1);
        final int numRootComponents = rootPath.getNameCount();
        long start = System.currentTimeMillis();
        final String fname = getCannonicalName(fl);
        final Path fPath = Paths.get(fname);
        final int numFileComponents = fPath.getNameCount();
        final BasicFileAttributes bfn;
        try {
            bfn = Files.readAttributes(fl.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            throw FSOrganizeException.raiseAndLog(
                "Could not find basic attributes of : " + fl.getName(), e, log);
        }
        
        final FileInfo finf = new FileInfo();
        final FileInfo.Type type = setNodeType(bfn, finf);
        
        final String lname;
        
        if (numRootComponents == numFileComponents) {
            lname = ".";
        } else {
            lname = fPath.subpath(numRootComponents, numFileComponents).toString();
        }
        finf.setName(lname);
        finf.setCreateDate(new Date(bfn.creationTime().toMillis()));
        finf.setBytes(bfn.size());
        if (type == Type.FILE) {
           final byte[] thumbnail = ThumbNailDAO.getThumbNail(fname);
            finf.setThumbnail(thumbnail);
           if (null != thumbnail) {
                FileAttribDAO.updateImageInfo(fl, finf);
            }
            final String checksum = ChecksumDAO.checksumFile(fl);
            finf.setChecksum(checksum);
        }
        long end = System.currentTimeMillis();
        finf.setProctime(end-start);
        log.debug("FileInfo Found: {}", finf);
        return finf;
    }
    public static FileInfo.Type setNodeType(final BasicFileAttributes bfn, final FileInfo finf) {
        final FileInfo.Type type;
        if (bfn.isDirectory()) {
            type = Type.DIRECTORY;
        } else if (bfn.isRegularFile()) {
            type = Type.FILE;
        } else {
            type = Type.OTHER;
        }
        finf.setType(type);
        return type;
    }
    public static String getCannonicalName(final File fl) {
        final String fname;
        try {
            fname = fl.getCanonicalPath();
        } catch (IOException e) {
            throw FSOrganizeException.raiseAndLog(
                    "Could not find canonical path of : " + fl.getName(), e, log);
        }
        return fname;
    }
    private FileInfoDAO() {}
}
