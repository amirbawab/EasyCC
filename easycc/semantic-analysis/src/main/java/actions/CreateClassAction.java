package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.models.DataModel;
import core.structure.SemanticStack;
import core.structure.symbol.SymbolTableTree;

@SemanticAction("createClassTableAndEntry")
public class CreateClassAction extends GenericAction {

    @ParsePhase(1)
    public void createClassTable(SemanticContext semanticContext, SemanticStack semanticStack, SymbolTableTree symbolTableTree) {
        symbolTableTree.getRelativeRootSymbolTable().addEntry(semanticContext.getEntry());
        semanticContext.getEntry().setName(((DataModel)semanticContext.getModel()).getLexicalToken().getValue());
    }
}
