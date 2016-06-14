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
    public void test_getDefaultMessage_success1() {
        assertEquals("Default message", SyntaxConfig.getInstance().getSyntaxMessageConfig().getDefaultMessage());
    }

    @Test
    public void test_getDefaultMessage_success2() {
        assertEquals("Default message", SyntaxConfig.getInstance().getMessage("random1", "random2"));
    }

    @Test
    public void test_getMessage_success1() {
        assertEquals("message one non-terminal", SyntaxConfig.getInstance().getMessage("one", null));
    }

    @Test
    public void test_getMessage_success2() {
        assertEquals("message one non-terminal", SyntaxConfig.getInstance().getMessage("one", "random"));
    }

    @Test
    public void test_getMessage_success3() {
        assertEquals("message two non-terminal terminal", SyntaxConfig.getInstance().getMessage("two", "two_prime"));
    }

    @Test
    public void test_getMessage_success4() {
        assertEquals("Default message", SyntaxConfig.getInstance().getMessage("two", "random"));
    }

    @Test
    public void test_getMessage_success5() {
        assertEquals("message three non-terminal", SyntaxConfig.getInstance().getMessage("three", "random"));
    }

    @Test
    public void test_getMessage_success6() {
        assertEquals("message three non-terminal terminal", SyntaxConfig.getInstance().getMessage("three", "three_prime"));
    }
}
