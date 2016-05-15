package token;

public class Token {
    private String token;
    private String value;
    private int row, col;
    private int position;

    public Token(String token, String value, int row, int col, int position) {
        this.token = token;
        this.value = value;
        this.row = row;
        this.col = col;
        this.position = position;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
