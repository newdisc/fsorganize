package nd.fsorganize.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JSONFileDAO<T> {
    private static Logger log = LoggerFactory.getLogger(JSONFileDAO.class);

    private final ObjectMapper jsonMapper;
    private static final ObjectMapper commonMapper = new ObjectMapper();
    private final TypeReference<T> typeOfT;
    static {
        commonMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public T readResource(final String resName) {
        final InputStream strm = JSONFileDAO.class.getResourceAsStream(resName);
        if (null == strm) {
            throw FSOrganizeException.raiseAndLog("Could not find Resource: " + resName, null, log);
        }
        try {
            return readObject(strm);
        } catch (IOException e) {
            throw FSOrganizeException.raiseAndLog("Issure reading resource file: " + resName, e, log);
        }
    }
    public T readFile(final String fname) {
        try (final FileInputStream fis = new FileInputStream(fname)){
            return readObject(fis);
        } catch (IOException e) {
            throw FSOrganizeException.raiseAndLog("Could not find file: " + fname, e, log);
        }
    }
    private T readObject(final InputStream strm) throws IOException {
        // deserialize contents of file into an object of type
        final T fileread = jsonMapper.readValue(strm, typeOfT);
        log.debug("Finished readingResource");
        return fileread;
    }
    public String objectToJson(final T obj) {
        return JSONFileDAO.objectToJsonS(jsonMapper,obj);
    }
    public static String objectToJsonS(final Object obj) {
        return JSONFileDAO.objectToJsonS(commonMapper, obj);
    }
    private static String objectToJsonS(final ObjectMapper mapper, final Object obj) {
        final String ret;
        try {
            ret = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw FSOrganizeException.raiseAndLog("Could not write object to String: " + obj, e, log);
        }
        return ret;
    }
    public static String getResourceFileName(final String resname) {
        final URL url = JSONFileDAO.class.getResource(resname);
        return url.getPath();
    }
    public JSONFileDAO(TypeReference<T> typeclass) {
        jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        typeOfT = typeclass;
    }
}

