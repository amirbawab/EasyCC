package core.config;

import core.annotation.Code;
import core.annotations.ParsePhase;
import core.code.GenericCodeGeneration;
import core.structure.symbol.SymbolTableTree;
import creator.CodeGenerationCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reflection.ObjectMethod;
import token.ActionToken;
import utils.StringUtilsPlus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles calls from semantic analyzer
 */

public class CodeHandler {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // List of actions to be called
    private List<GenericCodeGeneration> genericCodeGenerationList;

    // Method mapper
    private Map<String, ObjectMethod> codeMethodMap;

    // Singleton
    private static CodeHandler instance = new CodeHandler();

    private CodeHandler() {

        // Init components
        genericCodeGenerationList = new ArrayList<>();
        codeMethodMap = new HashMap<>();

        // Add all code generation classes
        new CodeGenerationCreator().allCodeGeneration(genericCodeGenerationList);

        // Register methods
        registerCodeGeneration();
    }

    /**
     * Register all the code generation instances created
     */
    public void registerCodeGeneration() {

        l.info("Registering code generation");

        // Loop on all created code generation classes
        for(GenericCodeGeneration codeGeneration : genericCodeGenerationList) {

            // Get annotation
            Code code = codeGeneration.getClass().getAnnotation(Code.class);

            if(code != null) {

                // Loop on all the methods
                for(Method method : codeGeneration.getClass().getMethods()) {

                    // Get annotation
                    ParsePhase parsePhase = method.getAnnotation(ParsePhase.class);

                    if(parsePhase != null) {

                        int currentParsePhase = parsePhase.value();
                        codeMethodMap.put(StringUtilsPlus.generateMethodKey(code.value().getName(), currentParsePhase), new ObjectMethod(method, codeGeneration));
                        l.info("Class: " + codeGeneration.getClass().getSimpleName() + " - Method: " + method.getName() + " - Semantic: " + code.value() + " - Phase: " + currentParsePhase + ", was registered!");
                    }
                }
            }
        }

        l.info("Finished registering code generation");
    }

    /**
     * Handle call for code generation
     * @param actionToken
     * @param phase
     * @param semanticContext
     */
    public void handleCode(ActionToken actionToken, int phase, SemanticContext semanticContext, SymbolTableTree symbolTableTree) {

        String key = StringUtilsPlus.generateMethodKey(actionToken.getValue(), phase);

        if(codeMethodMap.containsKey(key)) {
            ObjectMethod objectMethod = codeMethodMap.get(key);

            try {
                // Call method
                objectMethod.getMethod().invoke(objectMethod.getObject(), semanticContext, symbolTableTree);
            } catch (IllegalAccessException | InvocationTargetException e) {
                l.error(e.getMessage());
            }
        } else {
            l.warn("Code generation: " + actionToken.getValue() + " at Phase: " + phase + " is not handled by any method.");
        }
    }

    /**
     * Get singleton instance
     * @return singleton instance
     */
    public static CodeHandler getInstance() {
        return instance;
    }
}
