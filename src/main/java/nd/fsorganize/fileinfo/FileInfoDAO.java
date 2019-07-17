package nd.fsorganize.fileinfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import nd.fsorganize.fileinfo.FileInfo.Type;

@Slf4j
@Component
public class FileInfoDAO {
	public static FileInfo getFileInfo(final File fl) {
		long start = System.currentTimeMillis();
		final String fname = getCannonicalName(fl);
		final BasicFileAttributes bfn;
		try {
			bfn = Files.readAttributes(fl.toPath(), BasicFileAttributes.class);
		} catch (IOException e) {
			final String err = "Could not find basic attributes of : " + fl.getName();
			log.error(err);
			throw new RuntimeException(err, e);
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
		if (type == Type.FILE) {
			final String checksum = ChecksumDAO.checksumFile(fl);
			finf.setChecksum(checksum);
		}
		long end = System.currentTimeMillis();
		finf.setProctime(end-start);
		log.debug("FileInfo Found: " + finf);
		return finf;
	}
	public static String getCannonicalName(final File fl) {
		final String fname;
		try {
			fname = fl.getCanonicalPath();
		} catch (IOException e) {
			final String err = "Could not find canonical path of : " + fl.getName();
			log.error(err);
			throw new RuntimeException(err, e);
		}
		return fname;
	}
}
/*
	public static List<File> getFiles(final File rootdir){
		log.info("Processing: " + rootdir);
		final File[] matches = rootdir.listFiles();
		final List<File> orig = Arrays.asList(matches);
		log.info("Files: " + matches.length);
		final List<File> ret = orig.stream()
				.filter(f -> f.isDirectory())
				.map(FileInfoDAO::getFiles) //recurse
				.flatMap(lf->lf.stream())
				.collect(Collectors.toList());
		ret.addAll(orig);
		log.info("Found files/Dirs: " + ret.size());
		return ret;
	}
	public static Stream<File> getFileStream(final File dir) {
		final List<File> ret = getFiles(dir);//FileInfoDAO:: 
		return ret.stream();
	}
	public static File[] getArr(List<File> fl) {
		return fl.toArray(new File[0]);
	}
	public static List<File> getFiles(final File rootdir){
		log.info("Processing: " + rootdir);
		final File[] matches = rootdir.listFiles();
		final List<File> orig = Arrays.asList(matches);
		log.info("Files: " + matches.length);
		Stream<File> fstr = orig.stream();
		fstr = fstr.filter(f -> f.isDirectory());
		Stream<List<File>> flstr = fstr.map(FileInfoDAO::getFiles); //recurse
		fstr = flstr.flatMap(lf->lf.stream());
		List<File> ret = fstr.collect(Collectors.toList());
		ret.addAll(orig);
		log.info("Found files/Dirs: " + ret.size());
		return ret;
	}
 * 
 * 
 * */
/*
Stream<File[]> fastr = flstr.map(FileInfoDAO::getArr);
fstr = fastr.flatMap(Arrays::stream);
*/
/* // If we want to filter directories -- better leave to user
ret.addAll(orig.stream()
		.filter(f -> !f.isDirectory())
		.collect(Collectors.toList()));
*/
/*
		final List<File> ret = orig.stream()
				.filter(f -> f.isDirectory())
				.map(FileInfoDAO::getFileStream)//recurse
				.flatMap(Collections::stream)
				.collect(Collectors.toList());
		//recurse
		final List<FileInfo> reret = ret.stream().flatMap(FileInfoDAO::getFiles);
 * 
 * */
/*
public static List<FileInfo> getFiles(final File rootdir){
	if (!rootdir.isDirectory()) {
		log.error("Not a Directory: " + rootdir);
		return new ArrayList<FileInfo>();
	}
	final File[] matches = rootdir.listFiles();
	log.info("Matches: " + matches.length);
	final List<FileInfo> ret = Arrays.stream(matches)
		.map(FileInfoDAO::getFileInfo)
		.collect(Collectors.toList());
	//recurse
	final List<FileInfo> reret = ret.stream().flatMap(FileInfoDAO::getFiles)
	return ret;
}*/
