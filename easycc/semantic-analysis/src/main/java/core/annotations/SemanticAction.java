package core.annotations;

import enums.SemanticActionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify the semantic action that will run
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SemanticAction {
    SemanticActionEnum value();

    // If true, the semantic action will only be called if the parser is not in panic mode (error recovery)
    boolean stable() default true;
}
