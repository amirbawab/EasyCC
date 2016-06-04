package core.config;

import core.actions.GenericAction;
import core.annotations.ActionModel;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.models.DataModel;
import core.models.GenericModel;
import core.structure.SemanticStack;
import creator.ActionCreator;
import creator.ModelsFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import token.AbstractSyntaxToken;
import token.AbstractToken;
import token.ActionToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handle calls from the syntax analyzer
 */

public class SemanticHandler {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // List of actions to be called
    private List<GenericAction> actionList;

    // Method mapper
    private Map<String, ObjectMethod> actionMethodMap;
    private Map<String, Method> modelMethodMap;

    // Semantic stack
    private SemanticStack semanticStack;

    public SemanticHandler() {

        // Init components
        actionList = new ArrayList<>();
        actionMethodMap = new HashMap<>();
        modelMethodMap = new HashMap<>();
        semanticStack = new SemanticStack();

        // Get the actions
        new ActionCreator().allActions(actionList);

        // Register data
        registerActions();
        registerModels();
    }

    /**
     * Register all the action instances created
     */
    public void registerActions() {

        l.info("Registering semantic actions");

        // Loop on all created actions
        for(GenericAction action : actionList) {

            // Get annotation
            SemanticAction semanticAction = action.getClass().getAnnotation(SemanticAction.class);

            if(semanticAction != null) {

                // Loop on all the methods
                for(Method method : action.getClass().getMethods()) {

                    // Get annotation
                    ParsePhase parsePhase = method.getAnnotation(ParsePhase.class);

                    if(parsePhase != null) {
                        actionMethodMap.put(generateMethodKey(semanticAction.value(), parsePhase.value()), new ObjectMethod(method, action));
                        l.info("Class: " + action.getClass().getSimpleName() + " - Method: " + method.getName() + " - Semantic: " + semanticAction.value() + " - Phase: " + parsePhase.value() + ", was registered!");
                    }
                }
            }
        }

        l.info("Finished registering semantic actions");
    }

    /**
     * Register all the models methods created
     */
    public void registerModels() {

        l.info("Registering semantic models");

        // Loop on all the methods
        for(Method method : ModelsFactory.class.getMethods()) {

            // Get annotation
            ActionModel actionModel = method.getAnnotation(ActionModel.class);

            if(actionModel != null) {
                modelMethodMap.put(actionModel.value(), method);
                l.info("Model method: " + method.getName() + " - Semantic: " + actionModel.value() + ", was registered");
            }
        }

        l.info("Finished registering semantic models");
    }

    /**
     * Handle a semantic action
     * @param syntaxToken
     * @param lexicalToken
     * @param phase
     */
    public void handleAction(AbstractSyntaxToken syntaxToken, AbstractToken lexicalToken, int phase) {
        ActionToken actionToken = (ActionToken) syntaxToken;

        String key = generateMethodKey(actionToken.getValue(), phase);

        if(actionMethodMap.containsKey(key)) {
            ObjectMethod objectMethod = actionMethodMap.get(key);

            try {

                // Create the corresponding model
                GenericModel model;
                if(modelMethodMap.containsKey(actionToken.getValue())) {
                    model = (GenericModel) modelMethodMap.get(actionToken.getValue()).invoke(null);
                } else {
                    model = new DataModel();
                }

                // Set corresponding lexical token for data model
                if(model instanceof DataModel) {
                    ((DataModel) model).setLexicalToken(lexicalToken);
                }

                // Prepare a new semantic context
                SemanticContext semanticContext = new SemanticContext();
                semanticContext.setModel(model);

                // Call method
                objectMethod.getMethod().invoke(objectMethod.getGenericAction(), semanticStack, semanticContext);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            l.warn("Action: " + syntaxToken.getValue() + " at Phase: " + phase + " is not handled by any method.");
        }
    }

    /**
     * Generate a key for a method
     * @param action
     * @param phase
     * @return key
     */
    private String generateMethodKey(String action, int phase) {
        return action + "::" + phase;
    }

    /**
     * Store genericAction and its corresponding method
     */
    private class ObjectMethod {
        private Method method;
        private GenericAction genericAction;

        public ObjectMethod(Method method, GenericAction genericAction) {
            this.method = method;
            this.genericAction = genericAction;
        }

        public Method getMethod() {
            return method;
        }

        public Object getGenericAction() {
            return genericAction;
        }
    }
}
