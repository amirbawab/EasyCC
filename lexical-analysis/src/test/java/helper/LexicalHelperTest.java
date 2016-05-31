package helper;

import org.junit.BeforeClass;
import org.junit.Test;
import token.AbstractToken;
import token.LexicalToken;

import static org.junit.Assert.*;

public class LexicalHelperTest {

    // Shared token
    private static AbstractToken token;

    @BeforeClass
    public static void init() {
        // Create tokens
        token = new LexicalToken("test_token", "test_value", 10, 20, 30);
        AbstractToken tokenNext = new LexicalToken("test_token_next", "test_value_next", 11, 21, 31);
        AbstractToken tokenPrevious = new LexicalToken("test_token_previous", "test_value_previous", 12, 22, 32);

        // Link tokens
        token.setNext(tokenNext);
        token.setPrevious(tokenPrevious);
        tokenNext.setPrevious(token);
        tokenPrevious.setNext(token);
    }

    @Test
    public void test_messageReplace_success1() {
        String messages = "Test message ${lexical.token} ${lexical.next.token} ${lexical.next.previous.previous.token} Test message";
        String expected = "Test message " + token.getToken() + " " + token.getNext().getToken() + " " + token.getPrevious().getToken() + " Test message";
        assertEquals(expected, LexicalHelper.messageReplace(messages, token));
    }

    @Test
    public void test_messageReplace_success2() {
        String messages = "Test message ${lexical.value} ${lexical.next.value} ${lexical.next.previous.previous.value} Test message";
        String expected = "Test message " + token.getValue() + " " + token.getNext().getValue() + " " + token.getPrevious().getValue() + " Test message";
        assertEquals(expected, LexicalHelper.messageReplace(messages, token));
    }

    @Test
    public void test_messageReplace_success3() {
        String messages = "Test message ${lexical.line} ${lexical.next.line} ${lexical.next.previous.previous.line} Test message";
        String expected = "Test message " + token.getRow() + " " + token.getNext().getRow() + " " + token.getPrevious().getRow() + " Test message";
        assertEquals(expected, LexicalHelper.messageReplace(messages, token));
    }

    @Test
    public void test_messageReplace_success4() {
        String messages = "Test message ${lexical.column} ${lexical.next.column} ${lexical.next.previous.previous.column} Test message";
        String expected = "Test message " + token.getCol() + " " + token.getNext().getCol() + " " + token.getPrevious().getCol() + " Test message";
        assertEquals(expected, LexicalHelper.messageReplace(messages, token));
    }

    @Test
    public void test_messageReplace_wrong_format() {
        String messages = "Test message ${lexical.columns} ${lexical.next.column.value} ${lexical.next.token.previous.previous.column} Test message";
        assertEquals(messages, LexicalHelper.messageReplace(messages, token));
    }

    @Test
    public void test_messageReplace_token_out_of_bound() {
        String messages = "Test message ${lexical.next.next.column} ${lexical.previous.previous.column} Test message";
        assertEquals(messages, LexicalHelper.messageReplace(messages, token));
    }
}
