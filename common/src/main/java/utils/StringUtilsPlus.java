package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;

public class StringUtilsPlus {

    private static final org.apache.logging.log4j.Logger l = LogManager.getLogger();

    /**
     * Convert any object to a JSON string
     * @param object
     * @return JSON string
     */
    public static String ObjectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            l.error(e.getMessage());
        }
        return null;
    }

    /**
     * Convert a String array to an Object array
     * @param array
     * @return Object array
     */
    public static Object[] convertStringArrayToObjectArray(String[] array) {
        Object arrayObj[] = new Object[array.length];
        for (int i=0; i < array.length; i++) {
            arrayObj[i] = array[i];
        }
        return arrayObj;
    }

    /**
     * Convert a String table to an Object table
     * @param table
     * @return Object table
     */
    public static Object[][] convertStringTableToObjectTable(String[][] table) {
        Object tableObj[][] = new Object[table.length][];
        for (int row=0; row < table.length; row++) {
            tableObj[row] = new Object[table[row].length];
            for(int col=0; col < table[row].length; col++) {
                tableObj[row][col] = table[row][col];
            }
        }
        return tableObj;
    }

    /**
     * Generate a key for a method
     * @param action
     * @param phase
     * @return key
     */
    public static String generateMethodKey(String action, int phase) {
        return action + "::" + phase;
    }
}
