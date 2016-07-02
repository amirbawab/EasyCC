package token;

import token.structure.LexicalNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a non-terminal token
 */

public class NonTerminalToken extends AbstractSyntaxToken {

    // Parse tree: Common for LL and LR parser
    private List<AbstractSyntaxToken> children;

    // Lexical tree used to build an AST
    private LexicalNode lexicalNode;

    public NonTerminalToken(String value) {
        super(value);
        children = new ArrayList<>();
    }

    /**
     * Copy constructor
     * @param nonTerminalToken
     */
    public NonTerminalToken(NonTerminalToken nonTerminalToken) {
        super(nonTerminalToken);
        children = new ArrayList<>();
    }

    /**
     * Get children syntax tokens
     * @return children syntax tokens
     */
    public List<AbstractSyntaxToken> getChildren() {
        return children;
    }

    public LexicalNode getLexicalNode() {
        return lexicalNode;
    }

    public void setLexicalNode(LexicalNode lexicalNode) {
        this.lexicalNode = lexicalNode;
    }

    @Override
    public String getValue() {
        return getOriginalValue();
    }

    @Override
    public AbstractSyntaxToken copy() {
        return new NonTerminalToken(this);
    }
}
