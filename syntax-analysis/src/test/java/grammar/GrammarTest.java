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

    private static Grammar grammar1, grammar2, grammar3;

    private AbstractSyntaxToken N_A = new NonTerminalToken("A");
    private AbstractSyntaxToken N_B = new NonTerminalToken("B");
    private AbstractSyntaxToken N_C = new NonTerminalToken("C");
    private AbstractSyntaxToken N_D = new NonTerminalToken("D");
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
    private AbstractSyntaxToken T_a = new TerminalToken("'a'");
    private AbstractSyntaxToken T_b = new TerminalToken("'b'");
    private AbstractSyntaxToken T_c = new TerminalToken("'c'");
    private AbstractSyntaxToken T_d = new TerminalToken("'d'");
    private AbstractSyntaxToken T_f = new TerminalToken("'f'");
    private AbstractSyntaxToken T_g = new TerminalToken("'g'");

    private AbstractSyntaxToken EPSILON = new EpsilonToken();

    @BeforeClass
    public static void init() {
        grammar1 = new Grammar("/input/syntax-grammar1.cfg");
        grammar2 = new Grammar("/input/syntax-grammar2.cfg");
        grammar3 = new Grammar("/input/syntax-grammar3.cfg");
    }

    @Test
    public void test_firstSet_nonTerminals1() {

        Set<String> firstSetE = new HashSet<>(Arrays.asList("(", "id"));
        Set<String> firstSetX = new HashSet<>(Arrays.asList("+", "EPSILON"));
        Set<String> firstSetT = new HashSet<>(Arrays.asList("(", "id"));
        Set<String> firstSetY = new HashSet<>(Arrays.asList("*", "EPSILON"));
        Set<String> firstSetF = new HashSet<>(Arrays.asList("(", "id"));

        assertEquals(grammar1.getFirstSetOf(N_E), firstSetE);
        assertEquals(grammar1.getFirstSetOf(N_X), firstSetX);
        assertEquals(grammar1.getFirstSetOf(N_T), firstSetT);
        assertEquals(grammar1.getFirstSetOf(N_Y), firstSetY);
        assertEquals(grammar1.getFirstSetOf(N_F), firstSetF);
    }

    @Test
    public void test_firstSet_nonTerminals2() {

        Set<String> firstSetA = new HashSet<>(Arrays.asList("b", "g", "c", "d", "a"));
        Set<String> firstSetB = new HashSet<>(Arrays.asList("b", "EPSILON"));
        Set<String> firstSetC = new HashSet<>(Arrays.asList("c", "d", "a"));
        Set<String> firstSetD = new HashSet<>(Arrays.asList("d", "EPSILON"));
        Set<String> firstSetE = new HashSet<>(Arrays.asList("g", "c"));

        assertEquals(grammar2.getFirstSetOf(N_A), firstSetA);
        assertEquals(grammar2.getFirstSetOf(N_B), firstSetB);
        assertEquals(grammar2.getFirstSetOf(N_C), firstSetC);
        assertEquals(grammar2.getFirstSetOf(N_D), firstSetD);
        assertEquals(grammar2.getFirstSetOf(N_E), firstSetE);
    }

    @Test
    public void test_firstSet_nonTerminals3() {

        Set<String> firstSetA = new HashSet<>(Arrays.asList("b", "c", "EPSILON"));
        Set<String> firstSetB = new HashSet<>(Arrays.asList("b", "EPSILON"));
        Set<String> firstSetC = new HashSet<>(Arrays.asList("c", "EPSILON"));

        assertEquals(grammar3.getFirstSetOf(N_A), firstSetA);
        assertEquals(grammar3.getFirstSetOf(N_B), firstSetB);
        assertEquals(grammar3.getFirstSetOf(N_C), firstSetC);
    }

    @Test
    public void test_firstSet_terminals1() {

        Set<String> firstSetPlus = new HashSet<>(Arrays.asList("+"));
        Set<String> firstSetMultiply = new HashSet<>(Arrays.asList("*"));
        Set<String> firstSetOpen = new HashSet<>(Arrays.asList("("));
        Set<String> firstSetClose = new HashSet<>(Arrays.asList(")"));
        Set<String> firstSetId = new HashSet<>(Arrays.asList("id"));

        assertEquals(grammar1.getFirstSetOf(T_plus), firstSetPlus);
        assertEquals(grammar1.getFirstSetOf(T_multiply), firstSetMultiply);
        assertEquals(grammar1.getFirstSetOf(T_open), firstSetOpen);
        assertEquals(grammar1.getFirstSetOf(T_close), firstSetClose);
        assertEquals(grammar1.getFirstSetOf(T_id), firstSetId);
    }

    @Test
    public void test_firstSet_terminals2() {

        Set<String> firstSetA = new HashSet<>(Arrays.asList("a"));
        Set<String> firstSetB = new HashSet<>(Arrays.asList("b"));
        Set<String> firstSetC = new HashSet<>(Arrays.asList("c"));
        Set<String> firstSetD = new HashSet<>(Arrays.asList("d"));
        Set<String> firstSetF = new HashSet<>(Arrays.asList("f"));
        Set<String> firstSetG = new HashSet<>(Arrays.asList("g"));

        assertEquals(grammar2.getFirstSetOf(T_a), firstSetA);
        assertEquals(grammar2.getFirstSetOf(T_b), firstSetB);
        assertEquals(grammar2.getFirstSetOf(T_c), firstSetC);
        assertEquals(grammar2.getFirstSetOf(T_d), firstSetD);
        assertEquals(grammar2.getFirstSetOf(T_f), firstSetF);
        assertEquals(grammar2.getFirstSetOf(T_g), firstSetG);
    }

    @Test
    public void test_firstSet_epsilon1() {
        assertEquals(grammar1.getFirstSetOf(EPSILON), null);
    }

    @Test
    public void test_firstSet_epsilon2() {
        assertEquals(grammar2.getFirstSetOf(EPSILON), null);
    }

    @Test
    public void test_firstSet_epsilon3() {
        assertEquals(grammar3.getFirstSetOf(EPSILON), null);
    }

    @Test
    public void test_followSet_nonTerminals1() {

        Set<String> followSetE = new HashSet<>(Arrays.asList("$", ")"));
        Set<String> followSetX = new HashSet<>(Arrays.asList("$", ")"));
        Set<String> followSetT = new HashSet<>(Arrays.asList("+", "$", ")"));
        Set<String> followSetY = new HashSet<>(Arrays.asList("+", "$", ")"));
        Set<String> followSetF = new HashSet<>(Arrays.asList("*", "+", "$", ")"));

        assertEquals(grammar1.getFollowSetOf(N_E), followSetE);
        assertEquals(grammar1.getFollowSetOf(N_X), followSetX);
        assertEquals(grammar1.getFollowSetOf(N_T), followSetT);
        assertEquals(grammar1.getFollowSetOf(N_Y), followSetY);
        assertEquals(grammar1.getFollowSetOf(N_F), followSetF);
    }

    @Test
    public void test_followSet_nonTerminals2() {

        Set<String> followSetA = new HashSet<>(Arrays.asList("$", "f"));
        Set<String> followSetB = new HashSet<>(Arrays.asList("$", "a", "c", "d", "f", "g"));
        Set<String> followSetC = new HashSet<>(Arrays.asList("c", "d", "g"));
        Set<String> followSetD = new HashSet<>(Arrays.asList("a", "b", "c", "$", "f", "g"));
        Set<String> followSetE = new HashSet<>(Arrays.asList("a", "c", "d", "$", "f", "g"));

        assertEquals(grammar2.getFollowSetOf(N_A), followSetA);
        assertEquals(grammar2.getFollowSetOf(N_B), followSetB);
        assertEquals(grammar2.getFollowSetOf(N_C), followSetC);
        assertEquals(grammar2.getFollowSetOf(N_D), followSetD);
        assertEquals(grammar2.getFollowSetOf(N_E), followSetE);
    }

    @Test
    public void test_followSet_nonTerminals3() {

        Set<String> followSetA = new HashSet<>(Arrays.asList("$"));
        Set<String> followSetB = new HashSet<>(Arrays.asList("$", "c"));
        Set<String> followSetC = new HashSet<>(Arrays.asList("$"));

        assertEquals(grammar3.getFollowSetOf(N_A), followSetA);
        assertEquals(grammar3.getFollowSetOf(N_B), followSetB);
        assertEquals(grammar3.getFollowSetOf(N_C), followSetC);
    }

    @Test
    public void test_followSet_terminals1() {
        assertEquals(grammar1.getFollowSetOf(T_plus), null);
        assertEquals(grammar1.getFollowSetOf(T_multiply), null);
        assertEquals(grammar1.getFollowSetOf(T_open), null);
        assertEquals(grammar1.getFollowSetOf(T_close), null);
        assertEquals(grammar1.getFollowSetOf(T_id), null);
    }

    @Test
    public void test_followSet_terminals2() {
        assertEquals(grammar2.getFollowSetOf(T_a), null);
        assertEquals(grammar2.getFollowSetOf(T_b), null);
        assertEquals(grammar2.getFollowSetOf(T_c), null);
        assertEquals(grammar2.getFollowSetOf(T_d), null);
        assertEquals(grammar2.getFollowSetOf(T_f), null);
        assertEquals(grammar2.getFollowSetOf(T_g), null);
    }

    @Test
    public void test_followSet_epsilon1() {
        assertEquals(grammar1.getFollowSetOf(EPSILON), null);
    }

    @Test
    public void test_followSet_epsilon2() {
        assertEquals(grammar2.getFollowSetOf(EPSILON), null);
    }

    @Test
    public void test_followSet_epsilon3() {
        assertEquals(grammar3.getFollowSetOf(EPSILON), null);
    }

    @Test
    public void test_getStart_success1() {
        assertEquals(grammar1.getStart(), "E");
    }

    @Test
    public void test_getStart_success2() {
        assertEquals(grammar2.getStart(), "A");
    }

    @Test
    public void test_getStart_success3() {
        assertEquals(grammar3.getStart(), "A");
    }

    @Test
    public void test_getNonTerminals_success1() {
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("E", "X", "T", "Y", "F"));

        assertEquals(grammar1.getNonTerminals(), nonTerminals);
    }

    @Test
    public void test_getNonTerminals_success2() {
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("A", "B", "C", "D", "E"));

        assertEquals(grammar2.getNonTerminals(), nonTerminals);
    }

    @Test
    public void test_getNonTerminals_success3() {
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("A", "B", "C"));

        assertEquals(grammar3.getNonTerminals(), nonTerminals);
    }

    @Test
    public void test_getTerminals_success1() {
        Set<String> terminals = new HashSet<>(Arrays.asList("$", "+", "*", "(", ")", "id"));

        assertEquals(grammar1.getTerminals(), terminals);
    }

    @Test
    public void test_getTerminals_success2() {
        Set<String> terminals = new HashSet<>(Arrays.asList("$", "a", "b", "c", "d", "g", "f"));

        assertEquals(grammar2.getTerminals(), terminals);
    }

    @Test
    public void test_getTerminals_success3() {
        Set<String> terminals = new HashSet<>(Arrays.asList("$", "b", "c"));

        assertEquals(grammar3.getTerminals(), terminals);
    }
}
