package core.models;

import token.AbstractToken;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an Abstract Syntax Tree model which allows having a tree model of the
 * lexical tokens.
 * If the parser select is LR, then using this model will automatically build the tree, which
 * can be used in the semantic actions.
 * If the parser select is LL, the tree will be one node only but it can be populated using
 * the semantic stack by popping from and pushing into the stack.
 */

public class ASTModel extends GenericModel {
    private AbstractToken lexicalToken;
    protected List<ASTModel> childrenModels;

    public ASTModel() {
        childrenModels = new ArrayList<>();
    }

    public AbstractToken getLexicalToken() {
        return lexicalToken;
    }

    public void setLexicalToken(AbstractToken lexicalToken) {
        this.lexicalToken = lexicalToken;
    }
}