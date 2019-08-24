package nd.fsorganize.fileinfo;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import nd.fsorganize.util.FSOrganizeException;
import nd.fsorganize.util.FileCacheDAO;

public class FileInfoService {
    private static Logger log = LoggerFactory.getLogger(FileInfoService.class);
    public FileInfoTreeNode getFiles(final String directory){
        final FileInfoTreeNode root;
        final File rootdir = new File(directory);
        if (!rootdir.isDirectory()) {
            throw FSOrganizeException.raiseAndLog("Not a Directory: " + rootdir, null, log);
        }
        final List<FileInfo> ret = getFilesInfo(rootdir);
        final String rootcname = FileInfoDAO.getCannonicalName(rootdir);
        root = new FileInfoTreeNode(null, rootcname);
        root.populateTree(ret, rootcname);
        final String fileinfdb = rootcname + "/cache.fidb";
        FileCacheDAO<FileInfoTreeNode> cache = new FileCacheDAO<>(
                new TypeReference<FileInfoTreeNode>() {});
        cache.writeCache(fileinfdb, root);
        return root;
    }
    //2019-06-26 16:49:02 | WARN  | [main] n.f.FileInfoDAOTests:55 - Entire File List Time taken: 475094 default 3
    //2019-06-26 17:17:14 | WARN  | [main] n.f.FileInfoDAOTests:55 - Entire File List Time taken: 409941 with 7
    //2019-06-26 17:28:23 | WARN  | [main] n.f.FileInfoDAOTests:55 - Entire File List Time taken: 447478 with 14
    //2019-06-28 09:41:09 | WARN  | [main] n.f.FileInfoServiceTest:29 - Entire File List Time taken: 388739 with 10

    public List<FileInfo> getFilesInfo(final File rootdir) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        try {
            final String rootcanname = rootdir.getCanonicalPath();
            return forkJoinPool.submit(() ->
                //parallel task here, for example
                FileTraverseDAO.getFiles(rootdir)
                .parallel()
                .map( file -> FileInfoDAO.getFileInfo(file, rootcanname) )
                .collect(Collectors.toList())
                ).get();
        } catch (ExecutionException | IOException e) {
            throw FSOrganizeException.raiseAndLog("Could not find FileInfos of : " + rootdir, e, log);
        } catch (InterruptedException e) {
            FSOrganizeException a = FSOrganizeException.raiseAndLog("Could not find FileInfos of : " + rootdir, e, log);
            Thread.currentThread().interrupt();
            throw a;
        }
    }
}
