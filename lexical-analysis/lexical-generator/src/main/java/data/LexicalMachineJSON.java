package data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LexicalMachineJSON {

    @JsonProperty("states")
    List<LexicalStateJSON> states = new ArrayList<>();

    @JsonProperty("edges")
    List<LexicalEdgeJSON> edges = new ArrayList<>();
}
