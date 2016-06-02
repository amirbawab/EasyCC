package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.SemanticContext;

@SemanticAction("add")
public class AddAction extends GenericAction {

    @ParsePhase(1)
    public void print(SemanticContext semanticContext) {
        System.out.println("Add!");
    }
}
