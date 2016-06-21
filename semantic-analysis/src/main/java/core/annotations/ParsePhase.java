package core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify at what parsing phase a method will be executed.
 * If two methods in the same class and have the same parse phase value
 * then no order of execution is guaranteed
 *
 * The max value of all ParsePhase will determine how many times the syntax analyzer is
 * going to parse the code
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ParsePhase {
    int value();
}
