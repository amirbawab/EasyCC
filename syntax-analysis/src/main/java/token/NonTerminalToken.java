package token;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a non-terminal token
 */

public class NonTerminalToken extends AbstractSyntaxToken {
    private List<AbstractSyntaxToken> children;

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
     * Add a child syntax token
     * @param abstractSyntaxToken
     */
    public void addChild(AbstractSyntaxToken abstractSyntaxToken) {
        children.add(abstractSyntaxToken);
    }

    /**
     * Get children syntax tokens
     * @return children syntax tokens
     */
    public List<AbstractSyntaxToken> getChildren() {
        return children;
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
