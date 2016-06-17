package parser.strategy.SLR.structure.machine;

import token.AbstractSyntaxToken;

import java.util.ArrayList;
import java.util.List;

/**
 * A node in the LR state machine
 */

public class LRProduction {

    private AbstractSyntaxToken LHS;
    private List<AbstractSyntaxToken> RHS;

    public LRProduction() {
        RHS = new ArrayList<>();
    }

    public AbstractSyntaxToken getLHS() {
        return LHS;
    }

    public void setLHS(AbstractSyntaxToken LHS) {
        this.LHS = LHS;
    }

    public List<AbstractSyntaxToken> getRHS() {
        return RHS;
    }

    public void addToRHS(AbstractSyntaxToken token) {
        RHS.add(token);
    }
}
