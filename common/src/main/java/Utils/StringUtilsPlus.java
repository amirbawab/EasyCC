package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;

public class StringUtilsPlus {

    private static final org.apache.logging.log4j.Logger l = LogManager.getLogger();

    public static String ObjectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            l.error(e.getMessage());
        }
        return null;
    }
}
