package core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Action model specifies the type of a target lexicalToken
 * An action model is useful when the instance to be created has complex structure
 * For example: If an identifier can be an array, then a specific structure needs to be created for it
 * in order to store the indices
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ActionModel {
    String value();
}
