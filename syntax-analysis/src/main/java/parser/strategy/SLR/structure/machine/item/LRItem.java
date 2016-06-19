package parser.strategy.SLR.structure.machine.item;

import org.apache.commons.lang3.StringUtils;
import token.AbstractSyntaxToken;
import token.DotToken;

import java.util.ArrayList;
import java.util.List;

/**
 * A node in the LR state machine
 */

public class LRItem {

    private String LHS;
    private List<AbstractSyntaxToken> RHS;

    public LRItem() {
        RHS = new ArrayList<>();
    }

    /**
     * Copy constructor
     * @param item
     */
    public LRItem(LRItem item) {
        LHS = item.LHS;
        RHS = new ArrayList<>(item.getRHS());
    }

    public String getLHS() {
        return LHS;
    }

    public void setLHS(String LHS) {
        this.LHS = LHS;
    }

    public List<AbstractSyntaxToken> getRHS() {
        return RHS;
    }

    /**
     * Get syntax token positioned after the dot token
     * @return syntax token or null if dot is at the end of the list
     */
    public AbstractSyntaxToken getTokenAfterDot() {
        for(int i=0; i < RHS.size()-1; i++) {
            if(RHS.get(i) instanceof DotToken) {
                return RHS.get(i+1);
            }
        }
        return null;
    }

    /**
     * Shift the dot token one position to the right
     * @return true if dot token shifted, false if it is at the end of the list
     */
    public boolean shiftDotRight() {
        for(int i=0; i < RHS.size(); i++) {
            AbstractSyntaxToken token = RHS.get(i);
            if(token instanceof DotToken && i != RHS.size()-1) {
                AbstractSyntaxToken tmp = token;
                RHS.set(i, RHS.get(i+1));
                RHS.set(i+1, tmp);
                return true;
            }
        }
        return false;
    }

    /**
     * Make a copy of LRItem
     * @return copy of the item
     */
    @Override
    public LRItem clone() {
        return new LRItem(this);
    }

    @Override
    public boolean equals(Object o) {
        LRItem oItem = (LRItem) o;

        // Compare LHS
        if(!LHS.equals(oItem.getLHS())) {
            return false;
        }

        // Compare RHS size
        if(oItem.getRHS().size() != RHS.size()) {
            return false;
        }

        // Compare RHS tokens
        for(int i=0; i < RHS.size(); i++) {
            if(oItem.getRHS() == RHS.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return LHS + " -> " + StringUtils.join(RHS, " ");
    }
}
