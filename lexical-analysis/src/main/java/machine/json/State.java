package machine.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class State {

    public enum Type {
        INITIAL("initial"),
        NORMAL("normal"),
        FINAL("final");

        private String value;
        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("token")
    private String token;

    @JsonProperty("backtrack")
    private boolean backtrack;

    // Unique id
    private int id;

    // List of outgoing edges
    private List<Edge> outEdges = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        for(Type t : Type.values()){
            if(t.getValue().equals(type)){
                return t;
            }
        }
        return null;
    }

    public String getToken() {
        return token;
    }

    public boolean shouldBacktrack() {
        return backtrack;
    }

    public int getId() {
        return id;
    }

    public List<Edge> getOutEdges() {
        return outEdges;
    }

    /**
     * Get state when reading a value
     * @param value
     * @return new state
     */
    public State getStateOn(String value) {
        for(Edge edge : outEdges)
            if(edge.getValue().equals(value))
                return edge.getToState();
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBacktrack(boolean backtrack) {
        this.backtrack = backtrack;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        String result = String.format("%s - %s\n", name, type + (getType() == Type.FINAL ? " : " + token : ""));
        for(Edge edge : outEdges)
            result += String.format("\t%s\n", edge.toString());
        return result;
    }
}
