package core.models;

import token.structure.LexicalNode;

/**
 * This class represents an Abstract Syntax Tree model which allows having a tree model of the
 * lexical tokens.
 * If the parser select is LR, then using this model will automatically build the tree, which
 * can be used in the semantic actions.
 * If the parser select is LL, the tree will be one node only but it can be populated using
 * the semantic stack by popping from and pushing into the stack.
 */

public class ASTModel extends GenericModel {
    private LexicalNode lexicalNode;

    public LexicalNode getLexicalNode() {
        return lexicalNode;
    }

    public void setLexicalNode(LexicalNode lexicalNode) {
        this.lexicalNode = lexicalNode;
    }
}