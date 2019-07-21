package nd.fsorganize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import nd.fsorganize.fileinfo.FileInfoService;
import nd.fsorganize.fileinfo.FileInfoTreeNode;

@Slf4j
@RestController
public class InitController {

	@Autowired
	FileInfoService fileSvc;
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/init")
	public FileInfoTreeNode getFiles(
			@RequestParam
			final String directory) {
		log.info("Initializing File List: " + directory);
		FileInfoTreeNode ret = fileSvc.getFiles(directory);
		return ret;
	}
}
