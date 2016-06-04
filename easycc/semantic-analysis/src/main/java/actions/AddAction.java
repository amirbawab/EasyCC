package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.structure.SemanticStack;
import models.IntegerModel;

@SemanticAction("add")
public class AddAction extends GenericAction {

    @ParsePhase(1)
    public void addIntegers(SemanticStack semanticStack, SemanticContext semanticContext) {
        int newNumber = ((IntegerModel)semanticStack.pop()).getIntValue() + ((IntegerModel)semanticStack.pop()).getIntValue();
        System.out.println(newNumber);
    }
}
