package nd.fsorganize.fileinfo;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileInfoService {
	public FileInfoTreeNode getFiles(final String directory){
		final FileInfoTreeNode root;
		final File rootdir = new File(directory);
		if (!rootdir.isDirectory()) {
			final String err = "Not a Directory: " + rootdir;
			log.error(err);
			throw new RuntimeException(err);
		}
		final List<FileInfo> ret = getFilesInfo(rootdir);
		final String rootcname = FileInfoDAO.getCannonicalName(rootdir);
		root = new FileInfoTreeNode(null, rootcname);
		root.populateTree(ret, rootcname);
		return root;
	}
	//2019-06-26 16:49:02 | WARN  | [main] n.f.FileInfoDAOTests:55 - Entire File List Time taken: 475094 default 3
	//2019-06-26 17:17:14 | WARN  | [main] n.f.FileInfoDAOTests:55 - Entire File List Time taken: 409941 with 7
	//2019-06-26 17:28:23 | WARN  | [main] n.f.FileInfoDAOTests:55 - Entire File List Time taken: 447478 with 14
	//2019-06-28 09:41:09 | WARN  | [main] n.f.FileInfoServiceTest:29 - Entire File List Time taken: 388739 with 10

	public List<FileInfo> getFilesInfo(final File rootdir) {
		final ForkJoinPool forkJoinPool = new ForkJoinPool(10);
		try {
			return forkJoinPool.submit(() ->
			    //parallel task here, for example
				FileTraverseDAO.getFiles(rootdir)
				.parallel()
				.map(FileInfoDAO::getFileInfo)
				.collect(Collectors.toList())
				).get();
		} catch (InterruptedException | ExecutionException  e) {
			final String err = "Could not find FileInfos of : " + rootdir;
			log.error(err);
			throw new RuntimeException(err, e);
		}
	}

}
/*
if (full.length() < rootfilen) {
	log.error("Should not happen");
}
//if (!full.contentEquals(rootfinm)) {
if (true) {
	
}
		ret.stream().forEach(fi -> {
			populateTreeNode(fi);
		});
		log.info("Json of fileinfo: " + JSONFileDAO.objectToJson(root));
 * */
