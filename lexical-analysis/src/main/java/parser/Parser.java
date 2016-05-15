package parser;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Parser {

    private static final org.apache.logging.log4j.Logger l = LogManager.getLogger();
    public Lexical_Analysis lexical_analysis;

    public Parser() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream file = getClass().getResourceAsStream("/state-machine.json");
        try {
            lexical_analysis = mapper.readValue(file, Lexical_Analysis.class);
        } catch (IOException e) {
            l.error(e.getMessage());
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Lexical_Analysis {

    @JsonProperty("states")
    public List<State> states;

    @JsonProperty("edges")
    public List<Edge> edges;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class State {

    @JsonProperty("name")
    public String name;

    @JsonProperty("type")
    public String type;

    @JsonProperty("token")
    public String token;

    @JsonProperty("backtrack")
    public String backtrack;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Edge {
    @JsonProperty("from")
    public String from;

    @JsonProperty("to")
    public String to;

    @JsonProperty("value")
    public String value;
}
