package parser.strategy.SLR.structure.machine.item;

import org.apache.commons.lang3.StringUtils;
import token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A node in the LR state machine
 */

public class LRItem {

    private String LHS;
    private List<AbstractSyntaxToken> RHS;
    private List<AbstractSyntaxToken> rule;
    private static AbstractSyntaxToken dotToken = SyntaxTokenFactory.createDotToken();

    public LRItem(String LHS, List<AbstractSyntaxToken> rule) {
        this.rule = rule;
        this.LHS = LHS;
        RHS = new ArrayList<>();
        RHS.add(dotToken);
        for(AbstractSyntaxToken syntaxToken : rule) {
            if(syntaxToken instanceof TerminalToken || syntaxToken instanceof NonTerminalToken) {
                RHS.add(syntaxToken);
            }
        }
    }

    /**
     * Copy constructor
     * @param item
     */
    public LRItem(LRItem item) {
        LHS = item.LHS;
        rule = item.rule;
        RHS = new ArrayList<>(item.getRHS());
    }

    public String getLHS() {
        return LHS;
    }

    /**
     * Get rule
     * Note: This method should be used carefully.
     * Usually a copy of the rule is what is really needed.
     * @see #getRuleCopy()
     * @return
     */
    public List<AbstractSyntaxToken> getRule() {
        return rule;
    }

    /**
     * Get a copy of a rule
     * @return rule
     */
    public List<AbstractSyntaxToken> getRuleCopy() {
        return rule.stream().map(AbstractSyntaxToken::copy).collect(Collectors.toList());
    }

    public void setRule(List<AbstractSyntaxToken> rule) {
        this.rule = rule;
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
            if(oItem.getRHS().get(i) != RHS.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String output = LHS + " ->";
        for(AbstractSyntaxToken token: RHS) {
            output += " " + token.getValue();
        }
        return output;
    }
}
