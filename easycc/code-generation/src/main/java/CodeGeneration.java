import core.config.CodeHandler;
import core.config.SemanticContext;
import core.config.SemanticHandler;
import core.config.SemanticHandlerListener;
import core.structure.symbol.SymbolTableTree;
import token.ActionToken;

/**
 * This class connects semantic analyzer and code generation classes
 */

public class CodeGeneration {

    public CodeGeneration(SemanticAnalyzer semanticAnalyzer) {

        // Call to create the singleton instance
        CodeHandler.getInstance();

        // Register semantic handle listener
        SemanticHandler.getInstance().setSemanticHandlerListener(new SemanticHandlerListener() {
            @Override
            public void generateCode(ActionToken actionToken, int phase, SemanticContext semanticContext, SymbolTableTree symbolTableTree) {
                CodeHandler.getInstance().handleCode(actionToken, phase, semanticContext, symbolTableTree);
            }
        });
    }
}
