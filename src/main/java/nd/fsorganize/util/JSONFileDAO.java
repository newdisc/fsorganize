package nd.fsorganize.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

//TODO: Fix the template type to work with both Lists and Objects
public class JSONFileDAO<T> {
    private static Logger log = LoggerFactory.getLogger(JSONFileDAO.class);
    public List<T> readResource(final String resName, final TypeReference<List<T>> tr) {
        final InputStream strm = JSONFileDAO.class.getResourceAsStream(resName);
        if (null == strm) {
            throw FSOrganizeException.raiseAndLog("Could not find Resource: " + resName, null, log);
        }
        return (List<T>)readObject(tr, strm);
    }
    public T readFile(final String fname, final TypeReference<T> tr) {
        try {
            final T ret = (T)JSONFileDAO.readObject(tr, new FileInputStream(fname));
            return ret;
        } catch (FileNotFoundException e) {
            throw FSOrganizeException.raiseAndLog("Could not find file: " + fname, e, log);
        }
    }
    public static Object readObject(final TypeReference tr, final InputStream strm) {
        try {
        final ObjectMapper jsonMapper = new ObjectMapper();
        // deserialize contents of file into an object of type
        final Object fileread = jsonMapper.readValue(strm, tr);
        log.debug("Finished readingResource");
                //new TypeReference<List<T>>() {});//Using this form leads to typeerasure and us getting only list of maps
        return fileread;
        } catch (IOException e) {
            throw FSOrganizeException.raiseAndLog("Could not read Object from Stream ", e, log);
        }
    }
    public static String objectToJson(final Object obj) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
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
        final String path = url.getPath();
        return path;
    }
}

