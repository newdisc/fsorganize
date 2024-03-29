package nd.fsorganize.fileinfo;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTraverseDAO {
    private static Logger log = LoggerFactory.getLogger(FileTraverseDAO.class);

    public static Stream<File> getFiles(final File rootdir){
        Stream<File> ret = Stream.of(rootdir);
        final Stream<File> rret = getFilesRecurse(rootdir);
        ret = Stream.concat(ret, rret);
        return ret;
    }
    protected static Stream<File> getFilesRecurse(final File rootdir){
        log.debug("Processing: {}", rootdir);
        long start = System.currentTimeMillis();
        final File[] matches = rootdir.listFiles();
        long end = System.currentTimeMillis();
        log.debug("File List Time taken: {}", (end-start));
        log.debug("Files: {}", matches.length);
        Stream<File> ret = Arrays.asList(matches).parallelStream()//stream()//stream()
                .filter(File::isDirectory)
                .map(FileTraverseDAO::getFilesRecurse) //recurse only directories
                .flatMap(lf->lf);
        final Stream<File> orig = Arrays.stream(matches)
                .filter(f -> !f.getPath().matches(".*\\.sha3$"))
                .filter(f -> !f.getPath().matches(".*\\.class$"))
                .filter(f -> !f.getPath().matches(".*\\.fidb$"))
                .parallel();
        ret = Stream.concat(ret, orig);
        //log.debug("Found files/Dirs: " + ret.size());//with a stream will not know -- lazy
        return ret;
    }
    private FileTraverseDAO() {}
}
