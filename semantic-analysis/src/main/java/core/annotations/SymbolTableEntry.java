package core.annotations;

import enums.SemanticActionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Symbol table entry annotation specifies the type of entry an action should create
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SymbolTableEntry {
    SemanticActionEnum value();
}
