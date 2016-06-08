package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.structure.SemanticStack;
import core.structure.symbol.SymbolTableTree;

@SemanticAction("createClassTableAndEntry")
public class CreateClassAction extends GenericAction {

    @ParsePhase(1)
    public void createClassTable(SemanticContext semanticContext, SemanticStack semanticStack, SymbolTableTree symbolTableTree) {
        System.out.println(semanticContext.getModel().getClass().getSimpleName());
        System.out.println(semanticContext.getEntry().getClass().getSimpleName());
    }
}
