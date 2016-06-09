import core.config.SemanticHandler;
import core.structure.symbol.SymbolTableTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.ParseStrategyListener;
import token.AbstractSyntaxToken;
import token.AbstractToken;

public class SemanticAnalyzer {

    // Core components
    private SemanticHandler semanticHandler;

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    public SemanticAnalyzer(SyntaxAnalyzer syntaxAnalyzer) {

        // Set parse listener
        syntaxAnalyzer.getSyntaxParser().getParseStrategy().setParseStrategyListener(new ParseStrategyListener() {

            @Override
            public void init() {
                semanticHandler = new SemanticHandler();
            }

            @Override
            public void actionCall(AbstractSyntaxToken syntaxToken, AbstractToken lexicalToken, int phase) {
                semanticHandler.handleAction(syntaxToken, lexicalToken, phase);
            }

            @Override
            public int getParsePhase() {
                return semanticHandler.getMaxParsePhase();
            }

            @Override
            public void logSymbolTables() {
                l.info("Printing symbol tables\n" + semanticHandler.getSymbolTableTree());
            }
        });
    }

    public SymbolTableTree getSymbolTableTree() {
        return semanticHandler.getSymbolTableTree();
    }
}