package core.config;

import core.actions.GenericAction;
import core.annotations.ActionModel;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.annotations.SymbolTableEntry;
import core.models.ASTModel;
import core.models.DataModel;
import core.models.GenericModel;
import core.structure.SemanticStack;
import core.structure.symbol.SymbolTableTree;
import core.structure.symbol.table.entry.SymbolTableGenericEntry;
import creator.ActionCreator;
import creator.EntriesFactory;
import creator.ModelsFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reflection.ObjectMethod;
import token.*;
import token.structure.LexicalNode;
import utils.StringUtilsPlus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
    private Map<String, Method> entryMethodMap;

    // Cache semantic contexts for additional phases
    private Queue<SemanticContext> semanticContextsQueue;

    // Semantic stack
    private SemanticStack semanticStack;

    // Symbol table tree
    private SymbolTableTree symbolTableTree;

    // List of error messages
    private List<String> errorsList;

    // Store the number of parse phases
    private int maxParsePhase = 0;

    // Listener
    private SemanticHandlerListener semanticHandlerListener;

    // Singleton
    private static SemanticHandler instance = new SemanticHandler();

    private SemanticHandler() {

        // Init components
        actionList = new ArrayList<>();
        actionMethodMap = new HashMap<>();
        entryMethodMap = new HashMap<>();
        modelMethodMap = new HashMap<>();
        errorsList = new ArrayList<>();

        // Reset all data
        construct();

        // Get the actions
        new ActionCreator().allActions(actionList);

        // Register data
        registerActions();
        registerModels();
        registerEntries();
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

                        int currentParsePhase = parsePhase.value();

                        // Update max parse phase
                        if(currentParsePhase > maxParsePhase) {
                            maxParsePhase = currentParsePhase;
                        }

                        actionMethodMap.put(StringUtilsPlus.generateMethodKey(semanticAction.value().getName(), currentParsePhase), new ObjectMethod(method, action));
                        l.info("Class: " + action.getClass().getSimpleName() + " - Method: " + method.getName() + " - Semantic: " + semanticAction.value() + " - Phase: " + currentParsePhase + ", was registered!");
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
                modelMethodMap.put(actionModel.value().getName(), method);
                l.info("Model method: " + method.getName() + " - Semantic: " + actionModel.value() + ", was registered!");
            }
        }

        l.info("Finished registering semantic models");
    }

    /**
     * Register all the entries methods created
     */
    public void registerEntries() {

        l.info("Registering symbol table entries");

        // Loop on all the methods
        for(Method method : EntriesFactory.class.getMethods()) {

            // Get annotation
            SymbolTableEntry actionEntry = method.getAnnotation(SymbolTableEntry.class);

            if(actionEntry != null) {
                entryMethodMap.put(actionEntry.value().getName(), method);
                l.info("Entry method: " + method.getName() + " - Semantic: " + actionEntry.value() + ", was registered");
            }
        }

        l.info("Finished registering symbol table entries");
    }

    /**
     * Handle a semantic action
     * @param actionToken
     * @param phase
     */
    public void handleAction(ActionToken actionToken, int phase) {
        String key = null;

        try {

            // Common components
            SemanticContext semanticContext;
            GenericModel model = null;
            SymbolTableGenericEntry entry = null;

            if (phase == 1) {

                // In first phase create a semantic context
                semanticContext = new SemanticContext();

                // Check the type of parser
                if(actionToken instanceof LLActionToken) {

                    // Set key
                    key = StringUtilsPlus.generateMethodKey(actionToken.getValue(), phase);

                    LLActionToken llActionToken = (LLActionToken) actionToken;

                    // Create the corresponding model
                    if (modelMethodMap.containsKey(actionToken.getValue())) {
                        model = (GenericModel) modelMethodMap.get(actionToken.getValue()).invoke(null);
                    }

                    // Set corresponding lexical token for data model
                    if (model != null && model instanceof DataModel) {
                        ((DataModel) model).setLexicalToken(llActionToken.getLexicalToken());
                    }

                    // Set stability
                    semanticContext.setStable(actionToken.isStable());

                } else if(actionToken instanceof LRActionToken) {

                    LRActionToken lrActionToken = (LRActionToken) actionToken;

                    // Set key
                    key = StringUtilsPlus.generateMethodKey(lrActionToken.getName(), phase);

                    // Create the corresponding model
                    if (modelMethodMap.containsKey(lrActionToken.getName())) {
                        model = (GenericModel) modelMethodMap.get(lrActionToken.getName()).invoke(null);
                    }

                    // Get LHS
                     NonTerminalToken LHS = lrActionToken.getNonTerminalToken();

                    // Prepare lexical node
                    LexicalNode rootLexicalNode;

                    // If root was set
                    if(lrActionToken.getRoot() >= 0) {
                        // Set root lexical token
                        AbstractSyntaxToken rootLexicalToken = LHS.getChildren().get(lrActionToken.getRoot());
                        if (rootLexicalToken instanceof TerminalToken) {
                            rootLexicalNode = new LexicalNode();
                            rootLexicalNode.setLexicalToken((LexicalToken) ((TerminalToken) rootLexicalToken).getLexicalToken());

                        } else if (rootLexicalToken instanceof NonTerminalToken) {
                            rootLexicalNode = ((NonTerminalToken) rootLexicalToken).getLexicalNode();

                        } else {
                            throw new RuntimeException("Action token: " + actionToken.getValue() + ", root token has to be a terminal or a series of non-terminal ending in a terminal");
                        }
                    } else {
                        rootLexicalNode = new LexicalNode();
                    }

                    // Check if stable
                    rootLexicalNode.setStable(lrActionToken.isStable());

                    // Loop on children in action
                    for(int childId : lrActionToken.getChildren()) {
                        AbstractSyntaxToken child = LHS.getChildren().get(childId);
                        if(child instanceof NonTerminalToken) {
                            NonTerminalToken nonTerminalChild = (NonTerminalToken) child;

                            // If child doesn't have a node
                            if(nonTerminalChild.getLexicalNode() == null) {
                                rootLexicalNode.setStable(false);
                            } else {
                                rootLexicalNode.setStable(rootLexicalNode.isStable() && nonTerminalChild.getLexicalNode().isStable());
                            }
                            rootLexicalNode.getChildren().add(nonTerminalChild.getLexicalNode());

                        } else if(child instanceof TerminalToken) {
                            LexicalNode terminalLexicalNode = new LexicalNode();
                            rootLexicalNode.setLexicalToken((LexicalToken) ((TerminalToken) child).getLexicalToken());
                            rootLexicalNode.getChildren().add(terminalLexicalNode);
                        } else {
                            throw new RuntimeException("Action token: " + child.getOriginalValue() + " is neither a terminal nor a non-terminal in " + actionToken.getValue());
                        }
                    }

                    // Set stability
                    semanticContext.setStable(rootLexicalNode.isStable());

                    // Set corresponding lexical token for data model
                    if (model != null && model instanceof ASTModel) {
                        // Store root node in model
                        ((ASTModel) model).setLexicalNode(rootLexicalNode);
                    }

                    // Update lexical node for LHS
                    LHS.setLexicalNode(rootLexicalNode);
                }

                // Create symbol table entry
                if (entryMethodMap.containsKey(actionToken.getValue())) {
                    entry = (SymbolTableGenericEntry) entryMethodMap.get(actionToken.getValue()).invoke(null);
                    entry.setModel(model);
                }

                // Prepare a new semantic context
                semanticContext.setModel(model);
                semanticContext.setEntry(entry);

                // Add to the queue for additional phases
                semanticContextsQueue.offer(semanticContext);
            } else {

                // Enqueue the value removed from the queue
                semanticContext = semanticContextsQueue.poll();
                semanticContextsQueue.offer(semanticContext);
            }

            if(!actionToken.isSilent()) {

                // Check if semantic action is registered
                if (key != null && actionMethodMap.containsKey(key)) {
                    ObjectMethod objectMethod = actionMethodMap.get(key);
                    boolean actionClassStability = objectMethod.getObject().getClass().getAnnotation(SemanticAction.class).stableOnly();

                    // Call semantic action method
                    if (!actionClassStability || semanticContext.isStable()) {
                        objectMethod.getMethod().invoke(objectMethod.getObject(), semanticContext, semanticStack, symbolTableTree);
                    } else {
                        l.warn("Skipping semantic action call for '" + actionToken.getValue() + "' because the parser is in error recovery mode (unstable)");
                    }

                } else {
                    l.warn("Action: " + actionToken.getValue() + " at Phase: " + phase + " is not handled by any method.");
                }

                // Call code generation
                if (semanticHandlerListener != null) {
                    semanticHandlerListener.postSemanticHandler(actionToken, phase, semanticContext, symbolTableTree);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.getCause().printStackTrace();
        }
    }

    /**
     * Get the maximum parse phase
     * @return maximum of all ParsePhase annotations value
     */
    public int getMaxParsePhase() {
        return maxParsePhase;
    }

    /**
     * Get symbol table tree
     * @return symbol table tree
     */
    public SymbolTableTree getSymbolTableTree() {
        return symbolTableTree;
    }

    /**
     * Set semantic context listener
     * @param semanticHandlerListener
     */
    public void setSemanticHandlerListener(SemanticHandlerListener semanticHandlerListener) {
        this.semanticHandlerListener = semanticHandlerListener;
    }

    /**
     * Get singleton instance
     * @return singleton instance
     */
    public static SemanticHandler getInstance() {
        return instance;
    }

    /**
     * Reset instances
     */
    public void construct() {
        semanticStack = new SemanticStack();
        symbolTableTree = new SymbolTableTree();
        semanticContextsQueue = new LinkedList<>();
    }

    /**
     * Get list of error messages
     * @return error messages list
     */
    public List<String> getErrorsList() {
        return errorsList;
    }
}
