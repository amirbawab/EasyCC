package data.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure to convert custom data to 2D array of type Object
 */
public class ConsoleData<E extends ConsoleDataRow> {

    // Variables
    List<E> data;

    public ConsoleData() {
        this.data = new ArrayList<>();
    }

    /**
     * Add new entry
     * @param e
     */
    public void add(E e) {
        data.add(e);
    }

    /**
     * Convert array of data into a table
     * @return table of type Object
     */
    public Object[][] convertToObjectTable() {
        Object[][] objectData = new Object[data.size()][];
        for(int i=0; i < objectData.length; i++) {
            objectData[i] = data.get(i).convertToObjectArray();
        }
        return objectData;
    }
}
