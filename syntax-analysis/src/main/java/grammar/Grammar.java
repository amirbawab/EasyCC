package grammar;

import java.io.IOException;
import java.util.*;

import helper.SyntaxHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import token.*;

public class Grammar {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Variables
    private Map<String, List<List<AbstractSyntaxToken>>> productions;
    private Map<List<AbstractSyntaxToken>, Set<String>> ruleFirstSetMap;
    private Map<String, Set<String>> followSetMap;
    private String start;
    private Set<String> terminals;

    /**
     * Create grammar from file
     * @param file
     */
    public Grammar(String file) {

        try {

            // Init variables
            productions = new LinkedHashMap<>();
            ruleFirstSetMap = new HashMap<>();
            followSetMap = new HashMap<>();
            terminals = new HashSet<>();
            start = null;

            // Add EOS to terminal
            terminals.add(SyntaxHelper.END_OF_STACK);

            // Parse file
            parse(file);

            // Computer first
            computeFirst();
            l.info("First sets: %s", ruleFirstSetMap.toString());

            // Computer follow
            computeFollow();
            l.info("Follow sets: %s", followSetMap.toString());

        } catch (IOException e) {
            l.error(e.getMessage());
        }
    }

    /**
     * Get terminals
     * @return set of terminals
     */
    public Set<String> getTerminals() {
        return terminals;
    }

    /**
     * Get non terminals
     * @return set of non-terminals
     */
    public Set<String> getNonTerminals() {
        return productions.keySet();
    }

    /**
     * Get productions
     * @return productions map
     */
    public Map<String, List<List<AbstractSyntaxToken>>> getProductions() {
        return this.productions;
    }

    /**
     * Get first set map
     * @return first set map
     */
    public Map<List<AbstractSyntaxToken>, Set<String>> getRuleFirstSetMap() {
        return this.ruleFirstSetMap;
    }

    /**
     * Get follow set map
     * @return follow set map
     */
    public Map<String, Set<String>> getFollowSetMap() {
        return this.followSetMap;
    }

    /**
     * Compute the First set of all Non-Terminals
     */
    private void computeFirst() {

        // Rules iterator
        Iterator<Map.Entry<String, List<List<AbstractSyntaxToken>>>> it = productions.entrySet().iterator();

        // While more rules
        while (it.hasNext()) {
            computeFirst(SyntaxTokenFactory.createNonTerminalToken(it.next().getKey()));
        }
    }

    /**
     * Compute the first set of a non terminal recursively
     * @param token
     * @return first set of a non-terminal
     */
    private Set<String> computeFirst(AbstractSyntaxToken token) {

        // Optimize
        Set<String> tokenFirstSet = getFirstSetOf(token);
        if(token instanceof NonTerminalToken && !tokenFirstSet.isEmpty())
            return tokenFirstSet;

        // Get RHS
        List<List<AbstractSyntaxToken>> RHS = productions.get(token.getValue());

        // Loop on all productions
        for(List<AbstractSyntaxToken> production : RHS) {

            // Calculate first set for each rule
            Set<String> firstSet = new HashSet<>();

            // Loop on production tokens
            for(AbstractSyntaxToken syntaxToken : production) {

                // If terminal or epsilon
                if(syntaxToken instanceof TerminalToken || syntaxToken instanceof EpsilonToken) {
                    firstSet.add(syntaxToken.getValue());
                    break;

                } else if(syntaxToken instanceof NonTerminalToken) {

                    // Get first of token
                    Set<String> syntaxTokenFirstSet = computeFirst(syntaxToken);

                    // If doesn't have epsilon, or last token in the production
                    if(!syntaxTokenFirstSet.contains(SyntaxHelper.EPSILON) || syntaxToken == production.get(production.size()-1)) {

                        // Superset
                        firstSet.addAll(syntaxTokenFirstSet);

                        // Don't try next token
                        break;

                    } else {

                        // Superset minus epsilon
                        for(String str : syntaxTokenFirstSet) {
                            if (!str.equals(SyntaxHelper.EPSILON)) {
                                firstSet.add(str);
                            }
                        }
                    }
                }
            }

            // Add first set
            ruleFirstSetMap.put(production, firstSet);
        }

        // Get first set
        return getFirstSetOf(token);
    }


    /**
     * Compute the Follow set of all Non-Terminals
     */
    private void computeFollow() {

        // Loop on non terminals and init empty sets
        for(String nonTerminal : productions.keySet()) {
            followSetMap.put(nonTerminal, new HashSet<>());
        }

        // Add EOS to follow set of start token
        followSetMap.get(start).add(SyntaxHelper.END_OF_STACK);

        // Repeat the computation multiple times
        for (int repeatX = 0; repeatX < productions.size(); ++repeatX) {

            // Rules iterator
            Iterator<Map.Entry<String, List<List<AbstractSyntaxToken>>>> it = productions.entrySet().iterator();

            // While more productions
            while (it.hasNext()) {

                // Store current pair
                Map.Entry<String, List<List<AbstractSyntaxToken>>> current = it.next();

                // Loop on productions for the same key
                for(List<AbstractSyntaxToken> production : current.getValue()) {

                    // Loop on production syntax tokens
                    for(int i = 0; i < production.size(); ++i) {

                        // Current
                        AbstractSyntaxToken syntaxToken = production.get(i);

                        // If non terminal
                        if(syntaxToken instanceof NonTerminalToken) {

                            // While more tokens
                            int j = i;
                            while(++j < production.size()) {

                                // Get next token
                                AbstractSyntaxToken nextToken = production.get(j);

                                // Get the first set
                                Set<String> firstSet = getFirstSetOf(nextToken);

                                // If first set is defined
                                if(firstSet != null) {

                                    // Copy into follow set
                                    for (String str : firstSet) {
                                        if (!str.equals(SyntaxHelper.EPSILON)) {
                                            followSetMap.get(syntaxToken.getValue()).add(str);
                                        }
                                    }

                                    // If first set doesn't contain epsilon stop
                                    if (!firstSet.contains(SyntaxHelper.EPSILON))
                                        break;
                                }
                            }

                            // If no more next tokens and ended with epsilon
                            if(j == production.size()) {

                                // Include follow set of LHS into target
                                followSetMap.get(syntaxToken.getValue()).addAll(followSetMap.get(current.getKey()));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Get first set of a token
     * @param syntaxToken
     * @return first set
     */
    public Set<String> getFirstSetOf(AbstractSyntaxToken syntaxToken) {

        if(syntaxToken instanceof TerminalToken) {
            Set<String> firstSet = new HashSet<>();
            firstSet.add(syntaxToken.getValue());
            return firstSet;
        }

        if(syntaxToken instanceof NonTerminalToken) {
            Set<String> firstSet = new HashSet<>();
            for(List<AbstractSyntaxToken> rule : productions.get(syntaxToken.getValue())) {
                if(ruleFirstSetMap.containsKey(rule)) {
                    firstSet.addAll(ruleFirstSetMap.get(rule));
                }
            }
            return firstSet;
        }

        return null;
    }

    /**
     * Get follow set of a non terminal
     * @param syntaxToken
     * @return follow set of a non terminal
     */
    public Set<String> getFollowSetOf(AbstractSyntaxToken syntaxToken) {
        if(syntaxToken instanceof NonTerminalToken) {
            return this.followSetMap.get(syntaxToken.getValue());
        }
        return null;
    }

    /**
     * Get starting non terminal
     * @return starting non terminal
     */
    public String getStart() {
        return this.start;
    }

    /**
     * Parse grammar file
     * @param file
     * @throws IOException
     */
    private void parse(String file) throws IOException {
        // Scan file
        Scanner scanGrammar = new Scanner(this.getClass().getResource(file).openStream());

        // LHS
        String LHS = null;

        // While more lines to scan
        while(scanGrammar.hasNext()) {

            // Scan line
            String line = scanGrammar.nextLine();

            // Scan line
            Scanner scanLine = new Scanner(line);

            // Production
            List<AbstractSyntaxToken> production = new ArrayList<>();

            // If line is not empty
            if(scanLine.hasNext()) {

                // First
                String first = scanLine.next();

                if(first.equals("|")) {
                    // Nothing

                } else if(first.startsWith("%")) {

                    // Skip line because it's a comment
                    continue;

                } else {

                    // Update LHS
                    LHS = first;

                    // Start
                    if(start == null)
                        start = LHS;

                    // If first time create list
                    productions.putIfAbsent(LHS, new LinkedList<>());

                    // If next string is not ->
                    String insteadValue;
                    if(!(insteadValue = scanLine.next()).equals("->")){
                        scanLine.close();
                        throw new IOException("Wrong file format! Expecting -> instead of " + insteadValue);
                    }
                }
            }

            // While more words
            while(scanLine.hasNext()) {

                // Current word
                String current = scanLine.next();

                if(current.equals("|")) {

                    // Add to rule
                    productions.get(LHS).add(production);

                    // Create new array
                    production = new ArrayList<>();

                } else {
                    AbstractSyntaxToken token;

                    if(current.equals(SyntaxHelper.EPSILON)) {
                        token = SyntaxTokenFactory.createEpsilonToken();

                    } else if(current.equals(SyntaxHelper.END_OF_STACK)) {
                        token = SyntaxTokenFactory.createEndOfStackToken();

                    } else if(current.charAt(0) == '\'' && current.charAt(current.length()-1) == '\'') {
                        token = SyntaxTokenFactory.createTerminalToken(current);
                        terminals.add(token.getValue());

                    } else if(current.charAt(0) == '#' && current.charAt(current.length()-1) == '#') {
                        token = SyntaxTokenFactory.createActionToken(current);

                    } else {
                        token = SyntaxTokenFactory.createNonTerminalToken(current);
                    }
                    production.add(token);
                }
            }

            // Close scanner
            scanLine.close();

            // Add to rule
            if(LHS != null && production.size() > 0)
                productions.get(LHS).add(production);
        }

        // Close grammar scanner
        scanGrammar.close();
    }
}
