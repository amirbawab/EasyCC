import grammar.Grammar;
import jvm.LexicalArgs;
import jvm.SyntaxArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.SyntaxParser;
import parser.strategy.ParseStrategy;

import java.lang.reflect.Constructor;

public class SyntaxAnalyzer {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Syntax analyzer component
    private LexicalAnalyzer lexicalAnalyzer;
    private Grammar grammar;
    private SyntaxParser syntaxParser;

    public static void main(String[] args) {
        new SyntaxAnalyzer(new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.CONFIG)), System.getProperty(SyntaxArgs.GRAMMAR), System.getProperty(SyntaxArgs.PARSE_STRATEGY));
    }

    public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer, String grammarPath, String parseStrategyClass) {
        this.lexicalAnalyzer = lexicalAnalyzer;
        grammar = new Grammar(grammarPath);
        syntaxParser = new SyntaxParser(grammar, parseStrategyClass);
    }

    /**
     * Get parsed grammar
     * @return grammar
     */
    public Grammar getGrammar() {
        return grammar;
    }
}