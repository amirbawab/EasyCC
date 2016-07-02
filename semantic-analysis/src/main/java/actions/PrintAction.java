package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.models.ASTModel;
import core.structure.SemanticStack;
import core.structure.symbol.SymbolTableTree;
import enums.SemanticActionEnum;
import token.structure.LexicalNode;

@SemanticAction(value = SemanticActionEnum.PRINT)
public class PrintAction extends GenericAction {

    @ParsePhase(1)
    public void print(SemanticContext semanticContext, SemanticStack semanticStack, SymbolTableTree symbolTableTree) {
        ASTModel model = (ASTModel) semanticContext.getModel();

        System.out.println("\nPrinting AST");
        System.out.println("Stable: " + semanticContext.isStable());
        postOrder(model.getLexicalNode());
    }

    private void postOrder(LexicalNode node) {
        if(node != null) {
            for(LexicalNode childNode : node.getChildren()) {
                postOrder(childNode);
            }
            System.out.println(node.getLexicalToken().getValue());
        }
    }
}
