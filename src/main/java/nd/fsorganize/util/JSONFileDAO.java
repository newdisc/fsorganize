package nd.fsorganize.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JSONFileDAO<T> {
    private static Logger log = LoggerFactory.getLogger(JSONFileDAO.class);
    public List<T> readResource(final String resName, final TypeReference<List<T>> tr)
            throws IOException {
        final InputStream strm = JSONFileDAO.class.getResourceAsStream(resName);
        if (null == strm) {
            throw FSOrganizeException.raiseAndLog("Could not find Resource: " + resName, null, log);
        }
        final ObjectMapper jsonMapper = new ObjectMapper();
        // deserialize contents of file into an object of type
        final List<T> fileread = jsonMapper.readValue(strm, tr);
        log.debug("Finished readingResource");
                //new TypeReference<List<T>>() {});//Using this form leads to typeerasure and us getting only list of maps
        return fileread;
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
}

