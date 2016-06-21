package grammar;

import org.junit.BeforeClass;
import org.junit.Test;
import token.AbstractSyntaxToken;
import token.EpsilonToken;
import token.NonTerminalToken;
import token.TerminalToken;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class GrammarTest {

    private static Grammar grammar;
    private AbstractSyntaxToken N_E = new NonTerminalToken("E");
    private AbstractSyntaxToken N_X = new NonTerminalToken("X");
    private AbstractSyntaxToken N_T = new NonTerminalToken("T");
    private AbstractSyntaxToken N_Y = new NonTerminalToken("Y");
    private AbstractSyntaxToken N_F = new NonTerminalToken("F");

    private AbstractSyntaxToken T_plus = new TerminalToken("'+'");
    private AbstractSyntaxToken T_multiply = new TerminalToken("'*'");
    private AbstractSyntaxToken T_open = new TerminalToken("'('");
    private AbstractSyntaxToken T_close = new TerminalToken("')'");
    private AbstractSyntaxToken T_id = new TerminalToken("'id'");

    private AbstractSyntaxToken EPSILON = new EpsilonToken();

    @BeforeClass
    public static void init() {
        grammar = new Grammar("/input/syntax-grammar.cfg");
    }

    @Test
    public void test_firstSet_nonTerminals() {

        Set<String> firstSetE = new HashSet<>(Arrays.asList("(", "id"));
        Set<String> firstSetX = new HashSet<>(Arrays.asList("+", "EPSILON"));
        Set<String> firstSetT = new HashSet<>(Arrays.asList("(", "id"));
        Set<String> firstSetY = new HashSet<>(Arrays.asList("*", "EPSILON"));
        Set<String> firstSetF = new HashSet<>(Arrays.asList("(", "id"));

        assertEquals(grammar.getFirstSetOf(N_E), firstSetE);
        assertEquals(grammar.getFirstSetOf(N_X), firstSetX);
        assertEquals(grammar.getFirstSetOf(N_T), firstSetT);
        assertEquals(grammar.getFirstSetOf(N_Y), firstSetY);
        assertEquals(grammar.getFirstSetOf(N_F), firstSetF);
    }

    @Test
    public void test_firstSet_terminals() {

        Set<String> firstSetPlus = new HashSet<>(Arrays.asList("+"));
        Set<String> firstSetMultiply = new HashSet<>(Arrays.asList("*"));
        Set<String> firstSetOpen = new HashSet<>(Arrays.asList("("));
        Set<String> firstSetClose = new HashSet<>(Arrays.asList(")"));
        Set<String> firstSetId = new HashSet<>(Arrays.asList("id"));

        assertEquals(grammar.getFirstSetOf(T_plus), firstSetPlus);
        assertEquals(grammar.getFirstSetOf(T_multiply), firstSetMultiply);
        assertEquals(grammar.getFirstSetOf(T_open), firstSetOpen);
        assertEquals(grammar.getFirstSetOf(T_close), firstSetClose);
        assertEquals(grammar.getFirstSetOf(T_id), firstSetId);
    }

    @Test
    public void test_firstSet_epsilon() {
        assertEquals(grammar.getFirstSetOf(EPSILON), null);
    }

    @Test
    public void test_followSet_nonTerminals() {

        Set<String> followSetE = new HashSet<>(Arrays.asList("$", ")"));
        Set<String> followSetX = new HashSet<>(Arrays.asList("$", ")"));
        Set<String> followSetT = new HashSet<>(Arrays.asList("+", "$", ")"));
        Set<String> followSetY = new HashSet<>(Arrays.asList("+", "$", ")"));
        Set<String> followSetF = new HashSet<>(Arrays.asList("*", "+", "$", ")"));

        assertEquals(grammar.getFollowSetOf(N_E), followSetE);
        assertEquals(grammar.getFollowSetOf(N_X), followSetX);
        assertEquals(grammar.getFollowSetOf(N_T), followSetT);
        assertEquals(grammar.getFollowSetOf(N_Y), followSetY);
        assertEquals(grammar.getFollowSetOf(N_F), followSetF);
    }

    @Test
    public void test_followSet_terminals() {
        assertEquals(grammar.getFollowSetOf(T_plus), null);
        assertEquals(grammar.getFollowSetOf(T_multiply), null);
        assertEquals(grammar.getFollowSetOf(T_open), null);
        assertEquals(grammar.getFollowSetOf(T_close), null);
        assertEquals(grammar.getFollowSetOf(T_id), null);
    }

    @Test
    public void test_followSet_epsilon() {
        assertEquals(grammar.getFollowSetOf(EPSILON), null);
    }

    @Test
    public void test_getStart_success() {
        assertEquals(grammar.getStart(), "E");
    }

    @Test
    public void test_getNonTerminals_success() {
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("E", "X", "T", "Y", "F"));

        assertEquals(grammar.getNonTerminals(), nonTerminals);
    }

    @Test
    public void test_getTerminals_success() {
        Set<String> terminals = new HashSet<>(Arrays.asList("$", "+", "*", "(", ")", "id"));

        assertEquals(grammar.getTerminals(), terminals);
    }
}
