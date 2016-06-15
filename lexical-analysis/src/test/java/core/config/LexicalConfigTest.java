package core.config;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LexicalConfigTest {

    @BeforeClass
    public static void init() {
        LexicalConfig.getInstance().loadMessages("/input/lexical-messages.json");
        LexicalConfig.getInstance().loadTokens("/input/lexical-tokens.json");
    }

    @Test
    public void test_getMessage_success1() {
        assertEquals("message one", LexicalConfig.getInstance().getLexicalMessagesConfig().getMessage("terminal1"));
    }

    @Test
    public void test_getMessage_success2() {
        assertEquals("message two", LexicalConfig.getInstance().getLexicalMessagesConfig().getMessage("terminal2"));
    }

    @Test
    public void test_tokenIgnore_prefixIgnore() {
        assertEquals(true, LexicalConfig.getInstance().getLexicalTokensConfig().getIgnoreTokensConfig().isIgnoreToken("ignore_random"));
    }

    @Test
    public void test_tokenIgnore_suffixIgnore() {
        assertEquals(true, LexicalConfig.getInstance().getLexicalTokensConfig().getIgnoreTokensConfig().isIgnoreToken("random_ignore"));
    }

    @Test
    public void test_tokenIgnore_ignoreWordMatch() {
        assertEquals(true, LexicalConfig.getInstance().getLexicalTokensConfig().getIgnoreTokensConfig().isIgnoreToken("ignore"));
    }

    @Test
    public void test_tokenIgnore_ignoreExclude() {
        assertEquals(false, LexicalConfig.getInstance().getLexicalTokensConfig().getIgnoreTokensConfig().isIgnoreToken("ignore_no_ignore"));
    }

    @Test
    public void test_tokenIgnore_doNotIgnore() {
        assertEquals(false, LexicalConfig.getInstance().getLexicalTokensConfig().getIgnoreTokensConfig().isIgnoreToken("random"));
    }

    @Test
    public void test_tokenError_prefixError() {
        assertEquals(true, LexicalConfig.getInstance().getLexicalTokensConfig().getErrorTokensConfig().isErrorToken("error_random"));
    }

    @Test
    public void test_tokenError_suffixError() {
        assertEquals(true, LexicalConfig.getInstance().getLexicalTokensConfig().getErrorTokensConfig().isErrorToken("random_error"));
    }

    @Test
    public void test_tokenError_errorWordMatch() {
        assertEquals(true, LexicalConfig.getInstance().getLexicalTokensConfig().getErrorTokensConfig().isErrorToken("error"));
    }

    @Test
    public void test_tokenError_errorExclude() {
        assertEquals(false, LexicalConfig.getInstance().getLexicalTokensConfig().getErrorTokensConfig().isErrorToken("error_no_error"));
    }

    @Test
    public void test_tokenError_notAnError() {
        assertEquals(false, LexicalConfig.getInstance().getLexicalTokensConfig().getErrorTokensConfig().isErrorToken("random"));
    }

    @Test
    public void test_reservedToken_success1() {
        assertEquals("T_HELLO", LexicalConfig.getInstance().getLexicalTokensConfig().getReservedTokenOrDefault("hello", "should_be_HELLO"));
    }

    @Test
    public void test_reservedToken_success2() {
        assertEquals("should_be_this", LexicalConfig.getInstance().getLexicalTokensConfig().getReservedTokenOrDefault("random", "should_be_this"));
    }
}
