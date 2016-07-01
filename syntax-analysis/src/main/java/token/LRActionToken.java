package token;

import java.util.ArrayList;
import java.util.List;

/**
 * Action token for LR parser
 */

public class LRActionToken extends ActionToken {
    private AbstractSyntaxToken abstractSyntaxToken;
    private String name;
    private int root;
    private List<Integer> children;

    public LRActionToken(String value) {
        super(value);

        // Init components
        children = new ArrayList<>();

        // Trim value
        String trimValue = getValue();

        // Match the pattern name/root/child1,child2,...
        String[] parts = trimValue.split("/", -1);
        if(parts.length != 3) {
            throw new RuntimeException("Action token: " + value + " should be of the form %name/root/child1,child2,...%");
        }

        // Store the different parts
        name = parts[0];

        try {
            root = Integer.parseInt(parts[1]);
            for (String child : parts[2].split(",")) {
                if(!child.isEmpty()) {
                    children.add(Integer.parseInt(child));
                }
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Action token root and children should be of type integer");
        }
    }

    public LRActionToken(LRActionToken lrActionToken) {
        super(lrActionToken);
        abstractSyntaxToken = lrActionToken.abstractSyntaxToken;
        name = lrActionToken.name;
        root = lrActionToken.root;
        children = lrActionToken.children;
    }

    public AbstractSyntaxToken getAbstractSyntaxToken() {
        return abstractSyntaxToken;
    }

    public void setAbstractSyntaxToken(AbstractSyntaxToken abstractSyntaxToken) {
        this.abstractSyntaxToken = abstractSyntaxToken;
    }

    public String getName() {
        return name;
    }

    public int getRoot() {
        return root;
    }

    public List<Integer> getChildren() {
        return children;
    }

    @Override
    public AbstractSyntaxToken copy() {
        return new LRActionToken(this);
    }
}
