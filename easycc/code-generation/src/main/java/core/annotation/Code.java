package core.annotation;

import enums.SemanticActionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify the code generated at different phases
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Code {
    SemanticActionEnum value();
    boolean stableOnly() default false;
}
