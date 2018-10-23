package au.com.hypothesisconsulting.spachallenge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object objectToSerialize) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectToSerialize);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

}
