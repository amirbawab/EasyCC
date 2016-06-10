package code;

import core.annotation.Code;
import core.annotations.ParsePhase;
import core.code.GenericCodeGeneration;
import core.config.SemanticContext;
import core.structure.symbol.SymbolTableTree;
import enums.SemanticActionEnum;

@Code(SemanticActionEnum.CREATE_CLASS_TABLE_AND_ENTRY)
public class CreateClassCode extends GenericCodeGeneration {

    @ParsePhase(1)
    public void createClass(SemanticContext semanticContext, SymbolTableTree symbolTableTree) {
        System.out.println("Class created!");
    }
}
