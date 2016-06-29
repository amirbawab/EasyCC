package core.config;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SyntaxConfigTest {

    @BeforeClass
    public static void init() {
        SyntaxConfig.getInstance().loadMessages("/input/syntax-messages.json");
    }

    @Test
    public void test_getDefaultMessage_LL_success1() {
        assertEquals("Default message", SyntaxConfig.getInstance().getLLSyntaxMessageConfig().getDefaultMessage());
    }

    @Test
    public void test_getDefaultMessage_LL_success2() {
        assertEquals("Default message", SyntaxConfig.getInstance().getLLMessage("random1", "random2"));
    }

    @Test
    public void test_getMessage_LL_success1() {
        assertEquals("message one non-terminal", SyntaxConfig.getInstance().getLLMessage("one", null));
    }

    @Test
    public void test_getMessage_LL_success2() {
        assertEquals("message one non-terminal", SyntaxConfig.getInstance().getLLMessage("one", "random"));
    }

    @Test
    public void test_getMessage_LL_success3() {
        assertEquals("message two non-terminal terminal", SyntaxConfig.getInstance().getLLMessage("two", "two_prime"));
    }

    @Test
    public void test_getMessage_LL_success4() {
        assertEquals("Default message", SyntaxConfig.getInstance().getLLMessage("two", "random"));
    }

    @Test
    public void test_getMessage_LL_success5() {
        assertEquals("message three non-terminal", SyntaxConfig.getInstance().getLLMessage("three", "random"));
    }

    @Test
    public void test_getMessage_LL_success6() {
        assertEquals("message three non-terminal terminal", SyntaxConfig.getInstance().getLLMessage("three", "three_prime"));
    }

    @Test
    public void test_getDefaultMessage_LR_success1() {
        assertEquals("Default message", SyntaxConfig.getInstance().getLRSyntaxMessageConfig().getDefaultMessage());
    }

    @Test
    public void test_getDefaultMessage_LR_success2() {
        assertEquals("Default message", SyntaxConfig.getInstance().getLRMessage("random1", "random2"));
    }

    @Test
    public void test_getMessage_LR_success1() {
        assertEquals("message one error-key", SyntaxConfig.getInstance().getLRMessage("one", null));
    }

    @Test
    public void test_getMessage_LR_success2() {
        assertEquals("message one error-key", SyntaxConfig.getInstance().getLRMessage("one", "random"));
    }

    @Test
    public void test_getMessage_LR_success3() {
        assertEquals("message two error-key terminal", SyntaxConfig.getInstance().getLRMessage("two", "two_prime"));
    }

    @Test
    public void test_getMessage_LR_success4() {
        assertEquals("Default message", SyntaxConfig.getInstance().getLRMessage("two", "random"));
    }

    @Test
    public void test_getMessage_LR_success5() {
        assertEquals("message three error-key", SyntaxConfig.getInstance().getLRMessage("three", "random"));
    }

    @Test
    public void test_getMessage_LR_success6() {
        assertEquals("message three error-key terminal", SyntaxConfig.getInstance().getLRMessage("three", "three_prime"));
    }
}
