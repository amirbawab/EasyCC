import core.config.SemanticConfig;
import core.config.SemanticHandler;
import core.config.SemanticHandlerListener;
import core.structure.symbol.SymbolTableTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.ParseStrategyListener;
import token.AbstractSyntaxToken;
import token.AbstractToken;
import token.ActionToken;

public class SemanticAnalyzer {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    public SemanticAnalyzer(SyntaxAnalyzer syntaxAnalyzer, String semanticMessagesPath) {

        // Call to create the singleton instance
        SemanticHandler.getInstance();

        // Load messages from file
        SemanticConfig.getInstance().loadMessages(semanticMessagesPath);

        // Set parse listener
        syntaxAnalyzer.getSyntaxParser().getParseStrategy().setParseStrategyListener(new ParseStrategyListener() {

            @Override
            public void init() {
                SemanticHandler.getInstance().construct();
            }

            @Override
            public void actionCall(ActionToken actionToken, int phase) {
                SemanticHandler.getInstance().handleAction(actionToken, phase);
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
}