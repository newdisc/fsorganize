package nd.fsorganize.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

public class FileCacheDAO<T extends ICacheTransient> {
    private static Logger log = LoggerFactory.getLogger(FileCacheDAO.class);
    private final JSONFileDAO<T> jsondao = new JSONFileDAO<>();
    
    public T readCache(final String fname, TypeReference<T> tr) {
        final T ret = jsondao.readFile(fname, tr);
        ret.updateTransient();
        log.warn("Read cache");
        return ret;
    }
    
    public void writeCache(final String fname, Object cache) {
        final String strObject = JSONFileDAO.objectToJson(cache);
        log.warn("Write cache {}", strObject);
        try (PrintWriter out = new PrintWriter(fname)) {
            out.print(strObject);
        } catch (FileNotFoundException e) {
            final FSOrganizeException a = FSOrganizeException.raiseAndLog(
                    "Could NOT find cache file in Root Dir (should NOT happen): " + 
                            fname, e, log);
            throw a;
        }
    }
}
