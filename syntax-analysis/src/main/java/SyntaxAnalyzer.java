import config.SyntaxConfig;
import grammar.Grammar;
import jvm.LexicalArgs;
import jvm.SyntaxArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.SyntaxParser;
import token.AbstractToken;

public class SyntaxAnalyzer {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Syntax analyzer component
    private LexicalAnalyzer lexicalAnalyzer;
    private Grammar grammar;
    private SyntaxParser syntaxParser;

    // Process time
    private long syntaxAnalysisProcessTime;

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

    /**
     * Parse using the chosen parsing method
     * @param lexicalToken
     */
    public boolean parse(AbstractToken lexicalToken) {
        syntaxAnalysisProcessTime = System.currentTimeMillis();
        boolean parse = syntaxParser.getParseStrategy().parse(lexicalToken);
        syntaxAnalysisProcessTime = System.currentTimeMillis() - syntaxAnalysisProcessTime;

        // Log process time
        l.info("Syntax-analysis took: " + this.syntaxAnalysisProcessTime+ " ms");

        // Log symbol tables
        if(syntaxParser.getParseStrategy().getParseStrategyListener() != null) {
            syntaxParser.getParseStrategy().getParseStrategyListener().logSymbolTables();
        }

        return parse;
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

    /**
     * Get process time
     * @return process time in ms
     */
    public long getProcessTime() {
        return syntaxAnalysisProcessTime;
    }
}