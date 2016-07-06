package parser.strategy.LR.structure.machine.item;

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
    private int dotIndex;

    public LRItem(String LHS, List<AbstractSyntaxToken> rule) {
        this.rule = rule;
        this.LHS = LHS;
        RHS = new ArrayList<>();
        dotIndex = 0;
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
        dotIndex = item.dotIndex;
        RHS = new ArrayList<>(item.RHS);
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

    /**
     * Get syntax token positioned after the dot token
     * @return syntax token or null if dot is at the end of the list
     */
    public AbstractSyntaxToken getTokenAfterDot() {
        if(dotIndex != RHS.size()-1) {
            return RHS.get(dotIndex + 1);
        }
        return null;
    }

    /**
     * Shift the dot token one position to the right
     * @return true if dot token shifted, false if it is at the end of the list
     */
    public boolean shiftDotRight() {

        if(dotIndex != RHS.size()-1) {
            RHS.set(dotIndex, RHS.get(dotIndex+1));
            RHS.set(dotIndex+1, dotToken);
            dotIndex++;
            return true;
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
        if(oItem.RHS.size() != RHS.size()) {
            return false;
        }

        // Compare RHS tokens
        for(int i=0; i < RHS.size(); i++) {
            if(oItem.RHS.get(i) != RHS.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generate a key
     * @return key
     */
    public String getItemKey() {
        String key = "";
        for(int i = 0; i < RHS.size() && !(RHS.get(i) instanceof DotToken); i++) {
            key += "::" + RHS.get(i).getOriginalValue();
        }
        return key;
    }

    /**
     * Get error token just after the dot (if any)
     * @return error token
     */
    public List<ErrorKeyToken> getErrorTokens() {
        List<ErrorKeyToken> errorKeyTokenList = new ArrayList<>();

        AbstractSyntaxToken tokenBeforeDot = null;
        if(dotIndex > 0) {
            tokenBeforeDot = RHS.get(dotIndex-1);
        }

        // If initial item
        if(tokenBeforeDot == null) {
            int errorInt = 0;
            while(errorInt < rule.size() && rule.get(errorInt) instanceof ErrorKeyToken) {
                errorKeyTokenList.add((ErrorKeyToken) rule.get(errorInt++));
            }

        } else {
            for(int i=0; i < rule.size(); i++) {
                int errorInt = i+1;
                if(rule.get(i) == tokenBeforeDot) {
                    while(errorInt < rule.size() && rule.get(errorInt) instanceof ErrorKeyToken) {
                        errorKeyTokenList.add((ErrorKeyToken) rule.get(errorInt++));
                    }
                }
            }
        }

        return errorKeyTokenList;
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
