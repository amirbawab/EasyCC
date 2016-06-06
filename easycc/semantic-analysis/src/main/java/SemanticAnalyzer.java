import core.config.SemanticHandler;
import parser.strategy.ParseStrategyListener;
import token.AbstractSyntaxToken;
import token.AbstractToken;

public class SemanticAnalyzer {

    // Core components
    private SemanticHandler semanticHandler;

    public SemanticAnalyzer(SyntaxAnalyzer syntaxAnalyzer) {

        // Init components
        semanticHandler = new SemanticHandler();

        syntaxAnalyzer.getSyntaxParser().getParseStrategy().setParseStrategyListener(new ParseStrategyListener() {
            @Override
            public void actionCall(AbstractSyntaxToken syntaxToken, AbstractToken lexicalToken, int phase) {
                semanticHandler.handleAction(syntaxToken, lexicalToken, phase);
            }

            @Override
            public int getParsePhase() {
                return semanticHandler.getMaxParsePhase();
            }
        });
    }
}