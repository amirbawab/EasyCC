import core.actions.GenericAction;
import creator.ActionCreator;
import parser.strategy.ParseStrategyListener;
import token.AbstractSyntaxToken;
import token.AbstractToken;

import java.util.ArrayList;
import java.util.List;

public class SemanticAnalysis {

    // List of actions to be called
    private List<GenericAction> actionList;

    public SemanticAnalysis(SyntaxAnalyzer syntaxAnalyzer) {

        // Init list
        actionList = new ArrayList<>();

        // Get the actions
        new ActionCreator().allActions(actionList);

        syntaxAnalyzer.getSyntaxParser().getParseStrategy().setParseStrategyListener(new ParseStrategyListener() {
            @Override
            public void actionCall(AbstractSyntaxToken syntaxToken, AbstractToken lexicalToken, int phase) {

            }
        });
    }
}