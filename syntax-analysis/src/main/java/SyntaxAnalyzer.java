import config.SyntaxConfig;
import grammar.Grammar;
import jvm.LexicalArgs;
import jvm.SyntaxArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.SyntaxParser;
import token.LexicalToken;

import java.util.List;

public class SyntaxAnalyzer {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Syntax analyzer component
    private LexicalAnalyzer lexicalAnalyzer;
    private Grammar grammar;
    private SyntaxParser syntaxParser;

    public static void main(String[] args) {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.TOKENS), System.getProperty(LexicalArgs.MESSAGES) );
        new SyntaxAnalyzer(lexicalAnalyzer, System.getProperty(SyntaxArgs.GRAMMAR), System.getProperty(SyntaxArgs.PARSE_STRATEGY), System.getProperty(SyntaxArgs.MESSAGES));
    }

    public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer, String grammarPath, String parseStrategyClass, String messagesPath) {

        // Init components
        this.lexicalAnalyzer = lexicalAnalyzer;
        grammar = new Grammar(grammarPath);
        SyntaxConfig.getInstance().loadMessages(messagesPath);
        syntaxParser = new SyntaxParser(grammar, parseStrategyClass);
    }

    public void parse(List<LexicalToken> lexicalTokenList) {
        syntaxParser.getParseStrategy().parse(lexicalTokenList);
    }


    /**
     * Get parsed grammar
     * @return grammar
     */
    public Grammar getGrammar() {
        return grammar;
    }

    /**
     * Get syntax parser
     * @return syntax parser
     */
    public SyntaxParser getSyntaxParser() {
        return syntaxParser;
    }
}