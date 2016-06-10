import core.config.SemanticHandler;
import core.config.SemanticHandlerListener;
import core.structure.symbol.SymbolTableTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.ParseStrategyListener;
import token.AbstractSyntaxToken;
import token.AbstractToken;

public class SemanticAnalyzer {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    public SemanticAnalyzer(SyntaxAnalyzer syntaxAnalyzer) {

        // Call to create the singleton instance
        SemanticHandler.getInstance();

        // Set parse listener
        syntaxAnalyzer.getSyntaxParser().getParseStrategy().setParseStrategyListener(new ParseStrategyListener() {

            @Override
            public void init() {
                SemanticHandler.getInstance().construct();
            }

            @Override
            public void actionCall(AbstractSyntaxToken syntaxToken, AbstractToken lexicalToken, int phase) {
                SemanticHandler.getInstance().handleAction(syntaxToken, lexicalToken, phase);
            }

            @Override
            public int getParsePhase() {
                return SemanticHandler.getInstance().getMaxParsePhase();
            }

            @Override
            public void logSymbolTables() {
                l.info("Printing symbol tables\n" + SemanticHandler.getInstance().getSymbolTableTree());
            }
        });
    }

    /**
     * Set semantic handler listener
     * @param semanticHandlerListener
     */
    public void setSemanticHandlerListener(SemanticHandlerListener semanticHandlerListener) {
        SemanticHandler.getInstance().setSemanticHandlerListener(semanticHandlerListener);
    }

    /**
     * Get the symbol table tree
     * @return symbol table tree
     */
    public SymbolTableTree getSymbolTableTree() {
        return SemanticHandler.getInstance().getSymbolTableTree();
    }
}