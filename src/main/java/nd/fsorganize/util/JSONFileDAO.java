package nd.fsorganize.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSONFileDAO<T> {
    public List<T> readResource(final String resName, final TypeReference<List<T>> tr)
            throws IOException {
        final InputStream strm = JSONFileDAO.class.getResourceAsStream(resName);
        if (null == strm) {
            final String err = "Could not find Resource: " + resName;
            log.error(err);
            throw new FSOrganizeException(err);
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
        String ret;
        try {
            ret = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            final String err = "Could not write object to String: " + obj;
            log.error(err);
            throw new FSOrganizeException(err);
        }
        return ret;
    }
}

