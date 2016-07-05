package token.structure;

import token.LexicalToken;

import java.util.ArrayList;
import java.util.List;

/**
 * This class allows structuring a lexical token tree in different forms.
 * LR parser uses this class to build an AST
 */

public class LexicalNode {

    private LexicalToken lexicalToken;
    private List<LexicalNode> children;
    private boolean stable = true;

    public LexicalNode() {
        children = new ArrayList<>();
    }

    public LexicalToken getLexicalToken() {
        return lexicalToken;
    }

    public void setLexicalToken(LexicalToken lexicalToken) {
        this.lexicalToken = lexicalToken;
    }

    public List<LexicalNode> getChildren() {
        return children;
    }

    public void setChildren(List<LexicalNode> children) {
        this.children = children;
    }

    public boolean isStable() {
        return stable;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
    }
}
