package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.structure.SemanticStack;

/**
 * Handle action for integer
 */

@SemanticAction("integer")
public class IntegerAction extends GenericAction {

    @ParsePhase(1)
    public void foundInt(SemanticStack semanticStack, SemanticContext semanticContext) {
        semanticStack.push(semanticContext.getModel());
    }
}
