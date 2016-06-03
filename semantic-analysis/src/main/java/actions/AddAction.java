package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.structure.SemanticStack;

@SemanticAction("add")
public class AddAction extends GenericAction {

    @ParsePhase(1)
    public void print(SemanticStack semanticStack, SemanticContext semanticContext) {
        System.out.println("Add!");
    }
}
