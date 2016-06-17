package parser.strategy.SLR.structure;

import token.AbstractToken;

import java.util.ArrayList;
import java.util.List;

/**
 * A node in the LR state machine
 */

public class LRProduction {

    private AbstractToken LHS;
    private List<AbstractToken> RHS;

    public LRProduction() {
        RHS = new ArrayList<>();
    }

    public AbstractToken getLHS() {
        return LHS;
    }

    public void setLHS(AbstractToken LHS) {
        this.LHS = LHS;
    }

    public List<AbstractToken> getRHS() {
        return RHS;
    }

    public void addToRHS(AbstractToken token) {
        RHS.add(token);
    }
}
