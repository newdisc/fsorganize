package nd.fsorganize.fileinfo;

import java.util.Date;

import lombok.Data;

@Data
public class FileInfo {
	private String name;
	private String checksum;
	private Date createDate;
	private Type type;
	private long bytes;
	private long proctime;
	
	public static enum Type {
		FILE, DIRECTORY, OTHER
	}
}
