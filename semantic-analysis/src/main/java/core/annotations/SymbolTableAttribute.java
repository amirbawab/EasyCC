package core.annotations;

import enums.SymbolTableAttributeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation specifies under which symbol table attribute
 * a method return value should be placed
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SymbolTableAttribute {
    SymbolTableAttributeEnum value();
}