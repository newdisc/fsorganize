package nd.fsorganize.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

public class FileCacheDAO<T extends ICacheTransient> extends JSONFileDAO<T> {
    private static Logger log = LoggerFactory.getLogger(FileCacheDAO.class);
    
    public T readCache(final String fname) {
        final T ret = readFile(fname);
        ret.updateTransient();
        log.warn("Read cache");
        return ret;
    }
    
    public void writeCache(final String fname, T cache) {
        final String strObject = objectToJson(cache);
        log.debug("Write cache {}", strObject);
        writeStringToFile(fname, strObject);
    }

    public static void writeStringToFile(final String fname, final String strObject) {
        try (PrintWriter out = new PrintWriter(fname)) {
            out.print(strObject);
        } catch (FileNotFoundException e) {
            throw FSOrganizeException.raiseAndLog(
                    "Could NOT find cache file in Root Dir (should NOT happen): " + 
                            fname, e, log);
        }
    }
    public FileCacheDAO(TypeReference<T> typeclass) {
        super(typeclass);
    }
}
