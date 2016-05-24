package data;

/**
 * Class used to pass header and data from application to GUI
 */
public class GenericTable {
    private Object[] header;
    private Object[][] data;

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public Object[] getHeader() {
        return header;
    }

    public void setHeader(Object[] header) {
        this.header = header;
    }
}
