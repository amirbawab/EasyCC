package data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LexicalStateJSON {

    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("token")
    private String token;

    @JsonProperty("backtrack")
    private String backtrack;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBacktrack() {
        return backtrack;
    }

    public void setBacktrack(String backtrack) {
        this.backtrack = backtrack;
    }
}
